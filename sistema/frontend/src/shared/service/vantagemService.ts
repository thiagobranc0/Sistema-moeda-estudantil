import api from './api';

export interface Vantagem {
  id: string;
  descricao: string;
  custo: number;
  foto?: string;
  nome?: string;
  custoMoedas?: number;
  empresaId?: string;
  createdAt?: string;
  updatedAt?: string;
}

export const vantagemService = {
  getVantagem: async (empresaId: string, vantagemId: string): Promise<Vantagem> => {
    const response = await api.get<Vantagem>(`/empresa/${empresaId}/vantagens/${vantagemId}`);
    return response.data;
  },

  getVantagens: async (empresaId: string): Promise<Vantagem[]> => {
    const response = await api.get<Vantagem[]>(`/empresa/${empresaId}/vantagens`);
    return response.data;
  },

  getAllVantagens: async (): Promise<Vantagem[]> => {
    const response = await api.get<Vantagem[]>('/vantagens');
    return response.data;
  },

  createVantagem: async (empresaId: string, vantagemData: Omit<Vantagem, 'id'>): Promise<Vantagem> => {
    const response = await api.post<Vantagem>(`/empresa/${empresaId}/vantagens`, vantagemData);
    return response.data;
  },

  updateVantagem: async (empresaId: string, vantagemId: string, vantagemData: Partial<Vantagem>): Promise<Vantagem> => {
    const response = await api.put<Vantagem>(`/empresa/${empresaId}/vantagens/${vantagemId}`, vantagemData);
    return response.data;
  },

  deleteVantagem: async (empresaId: string, vantagemId: string): Promise<void> => {
    await api.delete(`/empresa/${empresaId}/vantagens/${vantagemId}`);
  },

  resgatar: async (alunoId: number, vantagemId: string): Promise<{ cupom: string; mensagem: string }> => {
    const response = await api.post<{ cupom: string; mensagem: string }>(`/alunos/${alunoId}/vantagens/${vantagemId}/resgatar`, {});
    return response.data;
  },
};
