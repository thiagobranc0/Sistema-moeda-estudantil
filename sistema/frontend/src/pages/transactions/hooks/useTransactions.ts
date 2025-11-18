import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { transactionService } from '../../../shared/service/transactionService';

export const useGetBalance = (userId: number | string | undefined) => {
  const isNumeric = typeof userId === 'number' || (typeof userId === 'string' && /^\d+$/.test(userId));
  const isValidId = userId !== undefined && userId !== '' && isNumeric && Number(userId) !== 0;
  const { data, isLoading, isError, error, refetch } = useQuery<number>({
    queryKey: ['GET_BALANCE', userId],
    queryFn: () => transactionService.getBalance(Number(userId)),
    enabled: isValidId,
  });

  return { balance: data ?? 0, isLoading, isError, error, refetch };
};

export const useGetUserInfo = (userId: number | string | undefined) => {
  const isNumeric = typeof userId === 'number' || (typeof userId === 'string' && /^\d+$/.test(userId));
  const isValidId = userId !== undefined && userId !== '' && isNumeric && Number(userId) !== 0;
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_USER_INFO', userId],
    queryFn: () => transactionService.getUserInfo(Number(userId)),
    enabled: isValidId,
  });

  return { user: data, isLoading, isError, error };
};

export const useGetDoacoesAluno = (alunoId: string | number | undefined) => {
  const isValidId = alunoId !== undefined && alunoId !== '' && alunoId !== null;
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_DOACOES_ALUNO', alunoId],
    queryFn: () => transactionService.getDoacoesAluno(alunoId as string | number),
    enabled: isValidId,
  });

  return { doacoes: data ?? [], isLoading, isError, error };
};

export const useGetSentByProfessor = (professorId: string | number | undefined) => {
  const isValidId = professorId !== undefined && professorId !== '' && professorId !== null;
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_DOACOES_PROFESSOR', professorId],
    queryFn: () => transactionService.getDoacoesProfessor(professorId as string | number),
    enabled: isValidId,
  });

  return { doacoes: data ?? [], isLoading, isError, error };
};

export const useEnviarDoacao = (professorId: string | number | undefined) => {
  const queryClient = useQueryClient();
  const isValidId = professorId !== undefined && professorId !== '' && professorId !== null;
  const mutation = useMutation({
    mutationFn: (payload: { email: string; valor: number; mensagem: string }) =>
      transactionService.enviarDoacao(professorId as string | number, payload),
    onSuccess: () => {
      if (isValidId) {
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
  const isNumeric = typeof userId === 'number' || (typeof userId === 'string' && /^\d+$/.test(userId));
  const isValidId = userId !== undefined && userId !== '' && isNumeric && Number(userId) !== 0;
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['GET_TRANSACTIONS', userId],
    queryFn: () => transactionService.getDoacoesAluno(Number(userId)),
    enabled: isValidId,
  });

  return { transactions: data ?? [], isLoading, isError, error };
};

export const useSendCoins = (professorId: string | number | undefined) => {
  const queryClient = useQueryClient();
  const isValidId = professorId !== undefined && professorId !== '' && professorId !== null;
  const mutation = useMutation({
    mutationFn: (payload: { email: string; valor: number; mensagem: string }) =>
      transactionService.enviarDoacao(professorId as string | number, payload),
    onSuccess: () => {
      if (isValidId) {
        queryClient.invalidateQueries({ queryKey: ['GET_BALANCE', professorId] });
        queryClient.invalidateQueries({ queryKey: ['GET_DOACOES_PROFESSOR', professorId] });
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
