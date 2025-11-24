import { useState } from 'react';
import {
  Box,
  Container,
  Typography,
  Card,
  CardContent,
  CardMedia,
  Grid,
  CircularProgress,
  Alert,
  Chip,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Pagination,
} from '@mui/material';
import { CalendarMonth as CalendarIcon } from '@mui/icons-material';
import { useAuth } from '../../shared/context/AuthContext';
import { useGetResgates } from './hooks/useGetResgates';
import Header from '../../shared/components/Header';
import type { Resgate } from '../../shared/service/resgatoService';

export default function ResgatesPage() {
  const { user } = useAuth();
  const { resgates, isLoading, isError, error } = useGetResgates(user?.email);
  const [viewMode, setViewMode] = useState<'grid' | 'table'>('table');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh">
        <CircularProgress size={60} />
      </Box>
    );
  }

  if (isError) {
    return (
      <Container>
        <Header />
        <Alert severity="error" sx={{ mt: 4 }}>
          Erro ao carregar resgates: {error?.message}
        </Alert>
      </Container>
    );
  }

  return (
    <Box sx={{ bgcolor: '#1a1a1a', minHeight: '100vh' }}>
      <Header />
      <Container maxWidth="lg" sx={{ py: 4 }}>
        <Box sx={{ mb: 4 }}>
          <Typography variant="h4" sx={{ fontWeight: 700, color: '#f0f0f0', mb: 2 }}>
            Meus Resgates
          </Typography>
          <Typography sx={{ color: '#b0b0b0', mb: 3 }}>
            Total de resgates: <strong>{resgates.length}</strong>
          </Typography>
        </Box>

        {resgates.length === 0 ? (
          <Alert severity="info" sx={{ bgcolor: '#3d3d3d', color: '#e0e0e0', border: '1px solid #555' }}>
            Você ainda não realizou nenhum resgate. Explore as vantagens disponíveis!
          </Alert>
        ) : (
          <>
            {/* Modo Grid (Cards) */}
            {viewMode === 'grid' && (
              <>
                <Grid container spacing={3}>
                  {resgates
                    .slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage)
                    .map((resgate: Resgate) => (
                  <Grid size={{ xs: 12, sm: 6, md: 4 }} key={resgate.id}>
                    <Card
                      sx={{
                        height: '100%',
                        display: 'flex',
                        flexDirection: 'column',
                        bgcolor: '#2d2d2d',
                        color: '#e0e0e0',
                        transition: 'transform 0.3s ease, box-shadow 0.3s ease',
                        '&:hover': {
                          transform: 'translateY(-4px)',
                          boxShadow: '0 8px 16px rgba(255, 107, 107, 0.2)',
                        },
                      }}
                    >
                      {resgate.foto && (
                        <CardMedia
                          component="img"
                          height="200"
                          image={resgate.foto}
                          alt={resgate.descricao}
                          sx={{ objectFit: 'cover' }}
                        />
                      )}
                      <CardContent sx={{ flexGrow: 1 }}>
                        <Typography variant="h6" sx={{ mb: 1, fontWeight: 600 }}>
                          {resgate.descricao}
                        </Typography>

                        <Box sx={{ display: 'flex', gap: 1, mb: 2, flexWrap: 'wrap' }}>
                          <Chip
                            label={`${resgate.custo} moedas`}
                            size="small"
                            sx={{
                              bgcolor: '#463a4dff',
                              color: '#f0f0f0',
                              fontWeight: 600,
                            }}
                          />
                          <Chip
                            icon={<CalendarIcon sx={{ color: '#b0b0b0 !important' }} />}
                            label={formatDate(resgate.dataResgate)}
                            size="small"
                            sx={{
                              bgcolor: '#3d3d3d',
                              color: '#b0b0b0',
                            }}
                          />
                        </Box>

                        <Typography variant="caption" sx={{ color: '#888', fontSize: '0.75rem' }}>
                          ID: {resgate.id.slice(0, 8)}...
                        </Typography>
                      </CardContent>
                    </Card>
                  </Grid>
                    ))}
                </Grid>
                {resgates.length > itemsPerPage && (
                  <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
                    <Pagination
                      count={Math.ceil(resgates.length / itemsPerPage)}
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

            {/* Modo Tabela */}
            {viewMode === 'table' && (
              <>
                <TableContainer component={Paper} sx={{ bgcolor: '#2d2d2d', border: '1px solid #3d3d3d' }}>
                  <Table>
                    <TableHead>
                      <TableRow sx={{ bgcolor: '#1a1a1a', borderBottomColor: '#463a4dff' }}>
                        <TableCell sx={{ color: '#f0f0f0', fontWeight: 700 }}>Descrição</TableCell>
                        <TableCell align="center" sx={{ color: '#f0f0f0', fontWeight: 700 }}>
                          Custo (moedas)
                        </TableCell>
                        <TableCell sx={{ color: '#f0f0f0', fontWeight: 700 }}>Data de Resgate</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {resgates
                        .slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage)
                        .map((resgate: Resgate) => (
                      <TableRow
                        key={resgate.id}
                        sx={{
                          bgcolor: '#2d2d2d',
                          borderBottomColor: '#3d3d3d',
                          '&:hover': {
                            bgcolor: '#3d3d3d',
                          },
                        }}
                      >
                        <TableCell sx={{ color: '#e0e0e0' }}>{resgate.descricao}</TableCell>
                        <TableCell align="center">
                          <Chip
                            label={`${resgate.custo}`}
                            size="small"
                            sx={{
                              bgcolor: '#463a4dff',
                              color: '#f0f0f0',
                              fontWeight: 600,
                            }}
                          />
                        </TableCell>
                        <TableCell sx={{ color: '#b0b0b0' }}>{formatDate(resgate.dataResgate)}</TableCell>
                      </TableRow>
                    ))}
                    </TableBody>
                  </Table>
                </TableContainer>
                {resgates.length > itemsPerPage && (
                  <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
                    <Pagination
                      count={Math.ceil(resgates.length / itemsPerPage)}
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

            {/* Seletor de Modo de Visualização */}
            <Box sx={{ mt: 4, display: 'flex', justifyContent: 'center', gap: 2 }}>
              <Chip
                label="Visualização em Cards"
                onClick={() => setViewMode('grid')}
                variant={viewMode === 'grid' ? 'filled' : 'outlined'}
                sx={{
                  bgcolor: viewMode === 'grid' ? '#ff6b6b' : 'transparent',
                  color: '#f0f0f0',
                  borderColor: '#ff6b6b',
                  fontWeight: 600,
                  cursor: 'pointer',
                }}
              />
              <Chip
                label="Visualização em Tabela"
                onClick={() => setViewMode('table')}
                variant={viewMode === 'table' ? 'filled' : 'outlined'}
                sx={{
                  bgcolor: viewMode === 'table' ? '#ff6b6b' : 'transparent',
                  color: '#f0f0f0',
                  borderColor: '#ff6b6b',
                  fontWeight: 600,
                  cursor: 'pointer',
                }}
              />
            </Box>
          </>
        )}
      </Container>
    </Box>
  );
}
