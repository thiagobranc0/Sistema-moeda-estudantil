import { useQuery } from '@tanstack/react-query';
import { resgatoService } from '../../../shared/service/resgatoService';

export function useGetResgates(emailAluno?: string) {
  const { data: resgates = [], isLoading, isError, error, refetch } = useQuery({
    queryKey: ['resgates', emailAluno],
    queryFn: () => resgatoService.getResgates(emailAluno!),
    enabled: !!emailAluno,
    staleTime: 1000 * 60 * 5, 
  });

  return { resgates, isLoading, isError, error, refetch };
}
