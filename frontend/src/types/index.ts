export interface User {
    id: string;
    username: string;
    email: string;
    role: 'SHOP_OWNER' | 'CUSTOMER';
}

export interface AuthResponse {
    token: string;
    id: string;
    username: string;
    email: string;
    role: 'SHOP_OWNER' | 'CUSTOMER';
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface RegisterRequest {
    username: string;
    email: string;
    password: string;
    role?: 'SHOP_OWNER' | 'CUSTOMER';
}

export interface Product {
    id: string;
    name: string;
    description: string;
    price: number;
    category: string;
    imageUrl?: string;
    shopId: string;
}

export interface ProductRequest {
    name: string;
    description: string;
    price: number;
    category: string;
    imageUrl?: string;
}

export interface AuthContextType {
    user: User | null;
    loading: boolean;
    login: (username: string, password: string) => Promise<boolean>;
    register: (username: string, email: string, password: string) => Promise<boolean>;
    logout: () => void;
}

export interface Shop {
    id: string;
    name: string;
    logoUrl?: string;
    email?: string;
    phone?: string;
    address?: string;
    city?: string;
    state?: string;
    zipCode?: string;
    description?: string;
    ownerId?: string;
    latitude?: number;
    longitude?: number;
} 