import { useMutation, useQueryClient } from "@tanstack/react-query";
import { vantagemService } from "../../../shared/service/vantagemService";
import type { Vantagem } from "../../../shared/service/vantagemService";

interface CreateVantagemParams {
  empresaId: string;
  vantagemData: Omit<Vantagem, 'id' | 'empresaId'>;
}

export const useCreateVantagem = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation<Vantagem, Error, CreateVantagemParams>({
    mutationFn: ({ empresaId, vantagemData }) => 
      vantagemService.createVantagem(empresaId, vantagemData),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({ queryKey: ["GET_VANTAGENS", variables.empresaId] });
    },
  });

  return {
    createVantagem: mutation.mutate,
    isLoading: mutation.isPending,
    isError: mutation.isError,
    isSuccess: mutation.isSuccess,
    error: mutation.error,
    data: mutation.data,
  };
};
