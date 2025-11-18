import { useMemo, useState } from 'react';
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
  Pagination,
} from '@mui/material';
import {
  Send as SendIcon,
  Download as DownloadIcon,
} from '@mui/icons-material';
import Header from '../../shared/components/Header';
import { useAuth } from '../../shared/context/AuthContext';
import { useGetBalance, useGetDoacoesAluno, useGetSentByProfessor } from '../transactions/hooks/useTransactions';
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
  const { user } = useAuth();
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;
  
  let tipoNormalizado = (user?.tipo || '').toUpperCase();
  if (tipoNormalizado === 'ALUNO') {
    tipoNormalizado = 'STUDENT';
  }
  const isStudent = tipoNormalizado === 'STUDENT';
  
  const { balance: fetchedBalance } = useGetBalance(userId);
  const balance = user?.saldo ?? fetchedBalance ?? 0;
  
  const { doacoes: alunoDoacao, isLoading: alunoLoading, isError: alunoError } = useGetDoacoesAluno(isStudent ? userId : undefined);
  const { doacoes: professorDoacao, isLoading: professorLoading, isError: professorError } = useGetSentByProfessor(!isStudent ? userId : undefined);
  
  const doacoes = isStudent ? alunoDoacao : professorDoacao;
  const isLoading = isStudent ? alunoLoading : professorLoading;
  const isError = isStudent ? alunoError : professorError;

  const sortedDoacoes = useMemo(() => {
    if (!doacoes || doacoes.length === 0) return [];
    return [...doacoes].sort((a, b) => {
      const dateA = new Date(a.dataDoacao || 0).getTime();
      const dateB = new Date(b.dataDoacao || 0).getTime();
      return dateB - dateA;
    });
  }, [doacoes]);

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
              <>
                <TableContainer sx={{ overflowX: 'auto' }}>
                  <Table>
                    <TableHead>
                      <TableRow sx={{ borderBottomColor: '#4a4a4a' }}>
                        <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Tipo</TableCell>
                        <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Valor</TableCell>
                        <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Professor</TableCell>
                        <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Mensagem</TableCell>
                        <TableCell sx={{ color: '#b0b0b0', fontWeight: 600 }}>Data</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {sortedDoacoes.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage).map((doacao: Doacao) => {
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
                                icon={getIcon(true)}
                                label={getLabel(true)}
                                sx={{
                                  bgcolor: '#252525',
                                  color: getColor(true),
                                  fontWeight: 600,
                                  borderColor: getColor(true),
                                  borderWidth: 1,
                                  borderStyle: 'solid',
                                }}
                              />
                            </TableCell>
                            <TableCell
                              sx={{
                                color: getColor(true),
                                fontWeight: 600,
                                fontSize: '1rem',
                              }}
                            >
                              +{doacao.valor}
                            </TableCell>
                            <TableCell sx={{ color: '#e0e0e0' }}>
                              {doacao.nomeProfessor}
                            </TableCell>
                            <TableCell sx={{ color: '#b0b0b0', maxWidth: '300px' }}>
                              {doacao.mensagem || '-'}
                            </TableCell>
                            <TableCell sx={{ color: '#b0b0b0' }}>
                              {doacao.dataDoacao
                                ? new Date(doacao.dataDoacao).toLocaleDateString('pt-BR', {
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
                {Math.ceil(sortedDoacoes.length / itemsPerPage) > 1 && (
                  <Box sx={{ display: 'flex', justifyContent: 'center', mt: 3 }}>
                    <Pagination
                      count={Math.ceil(sortedDoacoes.length / itemsPerPage)}
                      page={currentPage}
                      onChange={(_, page) => setCurrentPage(page)}
                      sx={{
                        '& .MuiPaginationItem-root': {
                          color: '#b0b0b0',
                        },
                        '& .MuiPaginationItem-page.Mui-selected': {
                          bgcolor: '#ff6b6b',
                          color: '#fff',
                        },
                      }}
                    />
                  </Box>
                )}
              </>
            )}
          </CardContent>
        </Card>
      </Container>
    </Box>
  );
}
