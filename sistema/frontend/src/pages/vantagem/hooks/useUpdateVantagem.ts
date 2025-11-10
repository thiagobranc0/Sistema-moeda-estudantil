import { useMutation, useQueryClient } from "@tanstack/react-query";
import { vantagemService } from "../../../shared/service/vantagemService";
import type { Vantagem } from "../../../shared/service/vantagemService";

interface UpdateVantagemParams {
  empresaId: string;
  vantagemId: string;
  vantagemData: Partial<Vantagem>;
}

export const useUpdateVantagem = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation<Vantagem, Error, UpdateVantagemParams>({
    mutationFn: ({ empresaId, vantagemId, vantagemData }) => 
      vantagemService.updateVantagem(empresaId, vantagemId, vantagemData),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({ queryKey: ["GET_VANTAGENS", variables.empresaId] });
      queryClient.invalidateQueries({ queryKey: ["GET_VANTAGEM", variables.empresaId, variables.vantagemId] });
    },
  });

  return {
    updateVantagem: mutation.mutate,
    isLoading: mutation.isPending,
    isError: mutation.isError,
    isSuccess: mutation.isSuccess,
    error: mutation.error,
    data: mutation.data,
  };
};
