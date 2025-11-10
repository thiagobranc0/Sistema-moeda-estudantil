import api from './api';

export interface User {
  id: string;
  name: string;
  email: string;
  role?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface UserResponse {
  data: User;
  message?: string;
}

export const userService = {
  getUser: async (id: string): Promise<User> => {
    const response = await api.get<UserResponse>(`/user/${id}`);
    return response.data.data;
  },

  getAllUsers: async (): Promise<User[]> => {
    const response = await api.get<{ data: User[] }>('/user');
    return response.data.data;
  },

  createUser: async (userData: Omit<User, 'id'>): Promise<User> => {
    const response = await api.post<UserResponse>('/user', userData);
    return response.data.data;
  },

  updateUser: async (id: string, userData: Partial<User>): Promise<User> => {
    const response = await api.put<UserResponse>(`/user/${id}`, userData);
    return response.data.data;
  },

  deleteUser: async (id: string): Promise<void> => {
    await api.delete(`/user/${id}`);
  },
};
