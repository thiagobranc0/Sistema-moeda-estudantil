import api from './api';

export interface UserBalance {
  id: number;
  nome: string;
  email: string;
  saldo: number;
  tipo: 'PROFESSOR' | 'STUDENT' | 'COMPANY';
}

export interface Doacao {
  id: string;
  professorId: number;
  alunoId: number;
  alunoEmail?: string;
  quantidade: number;
  motivo?: string;
  dataCriacao?: string;
}

export interface Transaction {
  id: string;
  type: 'SEND' | 'RECEIVE' | 'REDEEM';
  amount: number;
  from?: string;
  to?: string;
  message?: string;
  createdAt?: string;
}

export const transactionService = {
  // Get user balance
  getBalance: async (userId: number): Promise<number> => {
    const response = await api.get<UserBalance>(`/usuarios/${userId}`);
    return response.data.saldo;
  },

  // Get user info including balance
  getUserInfo: async (userId: number): Promise<UserBalance> => {
    const response = await api.get<UserBalance>(`/usuarios/${userId}`);
    return response.data;
  },

  // Get doacoes received by student
  getDoacoesAluno: async (alunoId: number): Promise<Doacao[]> => {
    const response = await api.get<Doacao[]>(`/alunos/${alunoId}/doacoes`);
    return response.data;
  },

  // Get doacoes sent by professor
  getDoacoesProfessor: async (professorId: number): Promise<Doacao[]> => {
    const response = await api.get<Doacao[]>(`/professores/${professorId}/doacoes`);
    return response.data;
  },

  // Professor sends coins (doação) to a student
  enviarDoacao: async (professorId: number, payload: { alunoId?: number; alunoEmail?: string; quantidade: number; motivo: string; }): Promise<Doacao> => {
    const response = await api.post<Doacao>(`/professores/${professorId}/doacoes`, payload);
    return response.data;
  },
};
