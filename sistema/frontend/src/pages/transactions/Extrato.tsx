import { useMemo } from 'react';
import { useParams } from 'react-router-dom';
import {
  Box,
  Container,
  Typography,
  Card,
  CardContent,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
  CircularProgress,
  Alert,
} from '@mui/material';
import {
  Send as SendIcon,
  Download as DownloadIcon,
} from '@mui/icons-material';
import Header from '../../shared/components/Header';
import { useGetBalance, useGetDoacoesAluno } from '../transactions/hooks/useTransactions';
import type { Doacao } from '../../shared/service/transactionService';

function getIcon(isReceived: boolean) {
  return isReceived ? (
    <DownloadIcon sx={{ color: '#8FBC8F' }} />
  ) : (
    <SendIcon sx={{ color: '#ff6b6b' }} />
  );
}

function getColor(isReceived: boolean) {
  return isReceived ? '#8FBC8F' : '#ff6b6b';
}

function getLabel(isReceived: boolean) {
  return isReceived ? 'Recebido' : 'Enviado';
}

export default function Extrato() {
  const { userId } = useParams<{ userId: string }>();
  const numUserId = Number(userId);
  const { balance, isLoading: balanceLoading } = useGetBalance(numUserId);
  const { doacoes, isLoading: doacoesLoading, isError } = useGetDoacoesAluno(numUserId);

  const sortedDoacoes = useMemo(() => {
    if (!doacoes || doacoes.length === 0) return [];
    return [...doacoes].sort((a, b) => {
      const dateA = new Date(a.dataCriacao || 0).getTime();
      const dateB = new Date(b.dataCriacao || 0).getTime();
      return dateB - dateA;
    });
  }, [doacoes]);

  const isLoading = balanceLoading || doacoesLoading;

  return (
    <Box sx={{ bgcolor: '#1a1a1a', minHeight: '100vh' }}>
      <Header />
      <Container maxWidth="lg" sx={{ py: 4 }}>
        {/* Balance Card */}
        <Card sx={{ bgcolor: '#2d2d2d', color: '#e0e0e0', mb: 4 }}>
          <CardContent>
            <Typography variant="h6" sx={{ color: '#b0b0b0', fontWeight: 500, mb: 1 }}>
              Saldo Atual
            </Typography>
            <Typography variant="h3" sx={{ color: '#f0f0f0', fontWeight: 700 }}>
              {balance} moedas
            </Typography>
          </CardContent>
        </Card>

        <Card sx={{ bgcolor: '#2d2d2d', color: '#e0e0e0' }}>
          <CardContent>
            <Typography variant="h5" sx={{ color: '#f0f0f0', fontWeight: 600, mb: 3 }}>
              Histórico de Doações
            </Typography>

            {isError && (
              <Alert severity="error" sx={{ mb: 2, bgcolor: '#3d2020', color: '#ff6b6b' }}>
                Erro ao carregar doações.
              </Alert>
            )}

            {isLoading ? (
              <Box sx={{ display: 'flex', justifyContent: 'center', py: 4 }}>
                <CircularProgress sx={{ color: '#f0f0f0' }} />
              </Box>
            ) : sortedDoacoes.length === 0 ? (
              <Typography sx={{ color: '#b0b0b0', textAlign: 'center', py: 4 }}>
                Nenhuma doação registrada.
              </Typography>
            ) : (
              <TableContainer sx={{ overflowX: 'auto' }}>
                <Table>
                  <TableHead>
                    <TableRow sx={{ borderBottomColor: '#4a4a4a' }}>
                      <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Tipo</TableCell>
                      <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Quantidade</TableCell>
                      <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>De / Professor</TableCell>
                      <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Motivo</TableCell>
                      <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Data</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {sortedDoacoes.map((doacao: Doacao) => {
                      const isReceived = true; // Este aluno sempre recebeu
                      return (
                        <TableRow
                          key={doacao.id}
                          sx={{
                            borderBottomColor: '#4a4a4a',
                            '&:hover': { bgcolor: '#353535' },
                          }}
                        >
                          <TableCell sx={{ color: '#e0e0e0' }}>
                            <Chip
                              icon={getIcon(isReceived)}
                              label={getLabel(isReceived)}
                              sx={{
                                bgcolor: '#252525',
                                color: getColor(isReceived),
                                fontWeight: 600,
                                borderColor: getColor(isReceived),
                                borderWidth: 1,
                                borderStyle: 'solid',
                              }}
                            />
                          </TableCell>
                          <TableCell
                            sx={{
                              color: getColor(isReceived),
                              fontWeight: 600,
                              fontSize: '1rem',
                            }}
                          >
                            +{doacao.quantidade}
                          </TableCell>
                          <TableCell sx={{ color: '#e0e0e0' }}>
                            Prof. ID: {doacao.professorId}
                          </TableCell>
                          <TableCell sx={{ color: '#b0b0b0', maxWidth: '300px' }}>
                            {doacao.motivo || '-'}
                          </TableCell>
                          <TableCell sx={{ color: '#b0b0b0' }}>
                            {doacao.dataCriacao
                              ? new Date(doacao.dataCriacao).toLocaleDateString('pt-BR', {
                                  year: 'numeric',
                                  month: '2-digit',
                                  day: '2-digit',
                                  hour: '2-digit',
                                  minute: '2-digit',
                                })
                              : '-'}
                          </TableCell>
                        </TableRow>
                      );
                    })}
                  </TableBody>
                </Table>
              </TableContainer>
            )}
          </CardContent>
        </Card>
      </Container>
    </Box>
  );
}
