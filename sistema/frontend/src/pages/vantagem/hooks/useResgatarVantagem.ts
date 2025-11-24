import { useMutation, useQueryClient } from '@tanstack/react-query';
import { vantagemService } from '../../../shared/service/vantagemService';

export function useResgatarVantagem(emailAluno?: string) {
  const queryClient = useQueryClient();

  const { mutate: resgatar, isPending: isResgatando, isError, error, isSuccess } = useMutation({
    mutationFn: (vantagemId: string) =>
      vantagemService.resgatar(String(emailAluno!), vantagemId),
    onSuccess: (data, variables) => {
      // Atualizar localStorage com o novo saldo
      const userDataStr = localStorage.getItem('user');
      if (userDataStr) {
        const userData = JSON.parse(userDataStr);
        // Obter custo da vantagem do contexto (será passado junto com vantagemId na chamada)
        // Para agora, fazemos uma abordagem simples: pegar o saldo atual e decrementar
        if (userData.saldo !== undefined) {
          // Se recebemos o novo saldo na resposta, usar ele
          if (data.saldoAtual !== undefined) {
            userData.saldo = data.saldoAtual;
          }
          localStorage.setItem('user', JSON.stringify(userData));
        }
      }
      
      queryClient.invalidateQueries({ queryKey: ['balance', emailAluno] });
      queryClient.invalidateQueries({ queryKey: ['userInfo', emailAluno] });
      queryClient.invalidateQueries({ queryKey: ['transactions'] });
      queryClient.invalidateQueries({ queryKey: ['resgates', emailAluno] });
    },
  });

  return { resgatar, isResgatando, isError, error, isSuccess };
}
