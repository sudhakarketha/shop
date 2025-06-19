import React, { useState, useEffect, FormEvent } from 'react';
import {
    Box,
    Typography,
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Alert,
    Grid,
    Card,
    CardContent,
    CardMedia,
    CardActions,
    Pagination,
    Avatar,
} from '@mui/material';
import axios from 'axios';
import { useAuth } from '../contexts/AuthContext';
import { Product, ProductRequest, Shop } from '../types';

interface PaginatedResponse {
    content: Product[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
}

// export interface Shop {
//     id: string;
//     name: string;
//     logoUrl?: string;
//     // ...other fields
// }

const Products: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [openDialog, setOpenDialog] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [formData, setFormData] = useState<ProductRequest>({
        name: '',
        description: '',
        price: 0,
        category: '',
    });
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const { user } = useAuth();
    const [shops, setShops] = useState<Shop[]>([]);
    const [selectedShop, setSelectedShop] = useState<Shop | null>(null);
    const [showMap, setShowMap] = useState(false);

    const fetchProducts = async (shopId?: string) => {
        try {
            let response;
            if (shopId) {
                response = await axios.get(`http://localhost:8080/api/shops/${shopId}/products`);
                setProducts(response.data.products || response.data || []);
            } else {
                response = await axios.get('http://localhost:8080/api/products');
                setProducts(response.data.content || response.data.products || []);
            }
            setError('');
        } catch (err) {
            setError('Failed to fetch products');
            setProducts([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        const fetchShops = async () => {
            const res = await axios.get('http://localhost:8080/api/shops');
            setShops(res.data.content || res.data.shops || []);
        };
        const fetchAllProducts = async () => {
            const res = await axios.get('http://localhost:8080/api/products');
            setProducts(res.data.content || res.data.products || []);
        };
        fetchShops();
        fetchAllProducts();
    }, []);

    useEffect(() => {
        fetchProducts(selectedShop?.id);
    }, [selectedShop]);

    const handlePageChange = (event: React.ChangeEvent<unknown>, value: number) => {
        setPage(value);
    };

    const handleOpenDialog = (product: Product | null = null) => {
        if (product) {
            setSelectedProduct(product);
            setFormData({
                name: product.name,
                description: product.description,
                price: product.price,
                category: product.category,
            });
        } else {
            setSelectedProduct(null);
            setFormData({
                name: '',
                description: '',
                price: 0,
                category: '',
            });
        }
        setOpenDialog(true);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setSelectedProduct(null);
        setFormData({
            name: '',
            description: '',
            price: 0,
            category: '',
        });
    };

    const handleSubmit = async (e?: React.FormEvent<HTMLFormElement> | React.MouseEvent<HTMLButtonElement>) => {
        if (e) e.preventDefault();
        if (!selectedShop) {
            setError('Please select a shop first.');
            return;
        }
        try {
            if (selectedProduct) {
                await axios.put(`http://localhost:8080/api/shops/${selectedShop.id}/products/${selectedProduct.id}`, formData);
            } else {
                await axios.post(`http://localhost:8080/api/shops/${selectedShop.id}/products`, formData);
            }
            handleCloseDialog();
            fetchProducts(selectedShop.id);
        } catch (err) {
            setError('Failed to save product');
            console.error('Error saving product:', err);
        }
    };

    const handleDelete = async (productId: string) => {
        if (window.confirm('Are you sure you want to delete this product?')) {
            if (!selectedShop) {
                setError('Please select a shop first.');
                return;
            }
            try {
                await axios.delete(`http://localhost:8080/api/shops/${selectedShop.id}/products/${productId}`);
                fetchProducts(selectedShop.id);
            } catch (err) {
                setError('Failed to delete product');
                console.error('Error deleting product:', err);
            }
        }
    };

    const handleBackToShops = () => {
        setSelectedShop(null);
        setError('');
    };

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setFormData({ ...formData, imageUrl: reader.result as string });
            };
            reader.readAsDataURL(file);
        }
    };

    if (loading) {
        return <Typography>Loading...</Typography>;
    }

    return (
        <Box>
            {selectedShop && (
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
                    <Typography variant="h4">Products</Typography>
                    {user?.role === 'SHOP_OWNER' && (
                        <Button variant="contained" onClick={() => handleOpenDialog()}>
                            Add Product
                        </Button>
                    )}
                </Box>
            )}

            {selectedShop && (
                <>
                    <Button onClick={() => setSelectedShop(null)} variant="outlined" sx={{ mb: 2 }}>
                        Back to All Products
                    </Button>
                    <Box sx={{ mb: 2 }}>
                        <Typography variant="h4">{selectedShop.name} Products</Typography>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={() => setShowMap(true)}
                            sx={{ mt: 1 }}
                        >
                            Show Location
                        </Button>
                    </Box>
                    {showMap && selectedShop.latitude && selectedShop.longitude && (
                        <Box sx={{ mb: 2 }}>
                            <iframe
                                width="100%"
                                height="300"
                                frameBorder="0"
                                style={{ border: 0 }}
                                src={`https://www.google.com/maps?q=${selectedShop.latitude},${selectedShop.longitude}&z=15&output=embed`}
                                allowFullScreen
                                title="Shop Location"
                            />
                        </Box>
                    )}
                </>
            )}

            {!selectedShop && (
                <Box sx={{ mt: 3 }}>
                    <Typography variant="h5" sx={{ mb: 2 }}>Select a Shop</Typography>
                    <Grid container spacing={3}>
                        {(shops || []).map((shop) => (
                            <Grid item key={shop.id}>
                                <Box
                                    onClick={() => setSelectedShop(shop)}
                                    sx={{
                                        display: 'flex',
                                        flexDirection: 'column',
                                        alignItems: 'center',
                                        cursor: 'pointer',
                                    }}
                                >
                                    <Avatar
                                        src={shop.logoUrl || 'https://via.placeholder.com/100'}
                                        alt={shop.name}
                                        sx={{ width: 100, height: 100, mb: 1, border: '2px solid #1976d2' }}
                                    />
                                    <Typography variant="subtitle1" align="center">{shop.name}</Typography>
                                </Box>
                            </Grid>
                        ))}
                    </Grid>
                </Box>
            )}

            {selectedShop && products.length === 0 && (
                <Typography variant="h6" color="text.secondary" sx={{ mt: 2 }}>
                    Products are not available
                </Typography>
            )}

            <Grid container spacing={3} sx={{ mt: 2 }}>
                {(products || []).map((product) => (
                    <Grid item xs={12} sm={6} md={4} key={product.id}>
                        <Card>
                            <CardMedia
                                component="img"
                                height="140"
                                image={product.imageUrl || 'https://via.placeholder.com/140'}
                                alt={product.name}
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5">{product.name}</Typography>
                                <Typography variant="body2" color="text.secondary">{product.description}</Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Shop: {shops.find(s => s.id === product.shopId)?.name || 'Unknown'}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <Dialog open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>{selectedProduct ? 'Edit Product' : 'Add Product'}</DialogTitle>
                <DialogContent>
                    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Name"
                            value={formData.name}
                            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Description"
                            multiline
                            rows={3}
                            value={formData.description}
                            onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Price"
                            type="number"
                            value={formData.price}
                            onChange={(e) => setFormData({ ...formData, price: Number(e.target.value) })}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            label="Category"
                            value={formData.category}
                            onChange={(e) => setFormData({ ...formData, category: e.target.value })}
                        />
                        <input
                            accept="image/*"
                            type="file"
                            style={{ display: 'none' }}
                            id="product-image-upload"
                            onChange={handleImageChange}
                        />
                        <label htmlFor="product-image-upload">
                            <Button variant="outlined" component="span" sx={{ mt: 2 }}>
                                Upload Product Image
                            </Button>
                        </label>
                        {formData.imageUrl && (
                            <Box sx={{ mt: 2 }}>
                                <img src={formData.imageUrl} alt="Preview" style={{ maxWidth: 120, maxHeight: 120 }} />
                            </Box>
                        )}
                    </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained">
                        {selectedProduct ? 'Update' : 'Add'}
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    );
};

export default Products; 