import { useMutation, useQueryClient } from "@tanstack/react-query";
import { vantagemService } from "../../../shared/service/vantagemService";

interface DeleteVantagemParams {
  empresaId: string;
  vantagemId: string;
}

export const useDeleteVantagem = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation<void, Error, DeleteVantagemParams>({
    mutationFn: ({ empresaId, vantagemId }) => 
      vantagemService.deleteVantagem(empresaId, vantagemId),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({ queryKey: ["GET_VANTAGENS", variables.empresaId] });
    },
  });

  return {
    deleteVantagem: mutation.mutate,
    isLoading: mutation.isPending,
    isError: mutation.isError,
    isSuccess: mutation.isSuccess,
    error: mutation.error,
  };
};
