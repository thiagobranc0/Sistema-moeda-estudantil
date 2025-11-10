import { useMutation } from "@tanstack/react-query";
import { authService } from "../../../shared/service/authService";
import type { LoginResponse } from "../../../shared/service/authService";

export const useLogin = () => {
  const mutation = useMutation<LoginResponse, Error, string>({
    mutationFn: (email: string) => authService.login(email),
  });

  return {
    login: mutation.mutate,
    isLoading: mutation.isPending,
    isError: mutation.isError,
    isSuccess: mutation.isSuccess,
    error: mutation.error,
    data: mutation.data,
  };
};
