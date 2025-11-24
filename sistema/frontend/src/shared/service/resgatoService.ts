import api from './api';

export interface Resgate {
  id: string;
  idAluno: string;
  email: string;
  idVantagem: string;
  descricao: string;
  custo: number;
  foto?: string;
  dataResgate: string;
}

export const resgatoService = {
  getResgates: async (emailAluno: string): Promise<Resgate[]> => {
    const response = await api.get<Resgate[]>(`/alunos/${emailAluno}/resgates`);
    return response.data;
  },
};
