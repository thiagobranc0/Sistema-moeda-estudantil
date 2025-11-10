import { useQuery } from "@tanstack/react-query";
import { vantagemService } from "../../../shared/service/vantagemService";
import type { Vantagem } from "../../../shared/service/vantagemService";

export const useGetVantagem = (empresaId: string, vantagemId: string) => {
  const { data: vantagem, isLoading, isError, error, refetch } = useQuery<Vantagem>({
    queryKey: ["GET_VANTAGEM", empresaId, vantagemId],
    queryFn: () => vantagemService.getVantagem(empresaId, vantagemId),
    enabled: !!empresaId && !!vantagemId,
  });

  return {
    vantagem,
    isLoading,
    isError,
    error,
    refetch,
  };
};

export const useGetVantagens = (empresaId: string) => {
  const { data: vantagens, isLoading, isError, error, refetch } = useQuery<Vantagem[]>({
    queryKey: ["GET_VANTAGENS", empresaId],
    queryFn: () => vantagemService.getVantagens(empresaId),
    enabled: !!empresaId,
  });

  return {
    vantagens: vantagens || [],
    isLoading,
    isError,
    error,
    refetch,
  };
};
