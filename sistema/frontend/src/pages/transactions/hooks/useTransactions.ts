import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { transactionService } from '../../../shared/service/transactionService';

export const useGetBalance = (userId: number | string | undefined) => {
  const { data, isLoading, isError, error, refetch } = useQuery<number>({
    queryKey: ['GET_BALANCE', userId],
    queryFn: () => transactionService.getBalance(Number(userId)),
    enabled: !!userId,
  });

  return { balance: data ?? 0, isLoading, isError, error, refetch };
};

export const useGetUserInfo = (userId: number | string | undefined) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_USER_INFO', userId],
    queryFn: () => transactionService.getUserInfo(Number(userId)),
    enabled: !!userId,
  });

  return { user: data, isLoading, isError, error };
};

export const useGetDoacoesAluno = (alunoId: number | string | undefined) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_DOACOES_ALUNO', alunoId],
    queryFn: () => transactionService.getDoacoesAluno(Number(alunoId)),
    enabled: !!alunoId,
  });

  return { doacoes: data ?? [], isLoading, isError, error };
};

export const useGetDoacoesProfessor = (professorId: number | string | undefined) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_DOACOES_PROFESSOR', professorId],
    queryFn: () => transactionService.getDoacoesProfessor(Number(professorId)),
    enabled: !!professorId,
  });

  return { doacoes: data ?? [], isLoading, isError, error };
};

export const useEnviarDoacao = (professorId: number | string | undefined) => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: (payload: { alunoId?: number; alunoEmail?: string; quantidade: number; motivo: string }) =>
      transactionService.enviarDoacao(Number(professorId), payload),
    onSuccess: () => {
      if (professorId) {
        queryClient.invalidateQueries({ queryKey: ['GET_BALANCE', professorId] });
        queryClient.invalidateQueries({ queryKey: ['GET_DOACOES_PROFESSOR', professorId] });
      }
    },
  });

  return {
    enviarDoacao: mutation.mutate,
    isSending: mutation.isPending,
    isError: mutation.isError,
    error: mutation.error,
    isSuccess: mutation.isSuccess,
  };
};

export const useGetTransactions = (userId: number | string | undefined) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_TRANSACTIONS', userId],
    queryFn: () => transactionService.getDoacoesAluno(Number(userId)),
    enabled: !!userId,
  });

  return { transactions: data ?? [], isLoading, isError, error };
};

export const useSendCoins = (fromId: number | string | undefined) => {
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: (payload: { to: string; amount: number; message: string }) =>
      transactionService.enviarDoacao(Number(fromId), {
        alunoEmail: payload.to,
        quantidade: payload.amount,
        motivo: payload.message,
      }),
    onSuccess: () => {
      if (fromId) {
        queryClient.invalidateQueries({ queryKey: ['GET_BALANCE', fromId] });
        queryClient.invalidateQueries({ queryKey: ['GET_DOACOES_PROFESSOR', fromId] });
      }
    },
  });

  return {
    sendCoins: mutation.mutate,
    isSending: mutation.isPending,
    isError: mutation.isError,
    error: mutation.error,
    isSuccess: mutation.isSuccess,
  };
};

export const useGetSentByProfessor = (professorId: number | string | undefined) => {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_SENT_BY_PROFESSOR', professorId],
    queryFn: () => transactionService.getDoacoesProfessor(Number(professorId)),
    enabled: !!professorId,
  });

  return { sent: data ?? [], isLoading, isError, error };
};
