import api from './api';

export interface LoginRequest {
  email: string;
}

export interface LoginResponse {
  id: number;
  nome: string;
  email: string;
  senha: string;
  cpf: string;
  rg: string;
  endereco: string;
  departamentoId?: number;
  cnpj?: string;
}

export const authService = {
  login: async (email: string): Promise<LoginResponse> => {
    const response = await api.post<LoginResponse>('/auth/login', { email });
    return response.data;
  },
};
