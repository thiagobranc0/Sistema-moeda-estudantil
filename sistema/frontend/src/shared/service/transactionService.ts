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
  idProfessor: string;
  nomeProfessor: string;
  idAluno: string;
  nomeAluno: string;
  mensagem: string;
  valor: number;
  dataDoacao: string;
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
  getBalance: async (userId: number): Promise<number> => {
    const response = await api.get<UserBalance>(`/usuarios/${userId}`);
    return response.data.saldo;
  },

  getUserInfo: async (userId: number): Promise<UserBalance> => {
    const response = await api.get<UserBalance>(`/usuarios/${userId}`);
    return response.data;
  },

  getDoacoesAluno: async (alunoId: string | number): Promise<Doacao[]> => {
    const response = await api.get<Doacao[]>(`/alunos/${alunoId}/doacoes`);
    return response.data;
  },

  getDoacoesProfessor: async (professorId: string | number): Promise<Doacao[]> => {
    const response = await api.get<Doacao[]>(`/professores/${professorId}/doacoes`);
    return response.data;
  },

  enviarDoacao: async (professorId: string | number, payload: { email: string; valor: number; mensagem: string }): Promise<Doacao> => {
    const response = await api.post<Doacao>(`/professores/${professorId}/doacoes`, payload);
    return response.data;
  },
};
