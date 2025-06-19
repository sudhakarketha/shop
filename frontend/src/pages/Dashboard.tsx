import React from 'react';
import { Box, Typography, Grid, Paper } from '@mui/material';
import { useAuth } from '../contexts/AuthContext';

const Dashboard: React.FC = () => {
    const { user } = useAuth();

    return (
        <Box>
            <Typography variant="h4" gutterBottom>
                Dashboard
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <Paper
                        sx={{
                            p: 3,
                            display: 'flex',
                            flexDirection: 'column',
                            height: 240,
                        }}
                    >
                        <Typography variant="h6" gutterBottom>
                            Welcome, {user?.username}!
                        </Typography>
                        <Typography variant="body1" color="text.secondary">
                            Role: {user?.role}
                        </Typography>
                        <Typography variant="body1" color="text.secondary">
                            Email: {user?.email}
                        </Typography>
                    </Paper>
                </Grid>
                <Grid item xs={12} md={6}>
                    <Paper
                        sx={{
                            p: 3,
                            display: 'flex',
                            flexDirection: 'column',
                            height: 240,
                        }}
                    >
                        <Typography variant="h6" gutterBottom>
                            Quick Actions
                        </Typography>
                        <Typography variant="body1" color="text.secondary">
                            • View and manage products
                        </Typography>
                        <Typography variant="body1" color="text.secondary">
                            • Update your profile
                        </Typography>
                        {user?.role === 'SHOP_OWNER' && (
                            <>
                                <Typography variant="body1" color="text.secondary">
                                    • Add new products
                                </Typography>
                                <Typography variant="body1" color="text.secondary">
                                    • Manage shop settings
                                </Typography>
                            </>
                        )}
                    </Paper>
                </Grid>
            </Grid>
        </Box>
    );
};

export default Dashboard; 