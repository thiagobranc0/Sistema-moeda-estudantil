import { useMutation, useQueryClient } from '@tanstack/react-query';
import { vantagemService } from '../../../shared/service/vantagemService';

export function useResgatarVantagem(alunoId?: string | number) {
  const queryClient = useQueryClient();

  const { mutate: resgatar, isPending: isResgatando, isError, error, isSuccess } = useMutation({
    mutationFn: (vantagemId: string) =>
      vantagemService.resgatar(String(alunoId!), vantagemId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['balance', alunoId] });
      queryClient.invalidateQueries({ queryKey: ['userInfo', alunoId] });
    },
  });

  return { resgatar, isResgatando, isError, error, isSuccess };
}
