import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import {
  Box,
  Container,
  Typography,
  Button,
  Card,
  CardContent,
  CardActions,
  CardMedia,
  Grid,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  CircularProgress,
  Alert,
  IconButton,
  Chip,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  CardGiftcard as VoucherIcon,
  ShoppingCart as ShoppingCartIcon,
  WarningAmberOutlined as WarningIcon,
} from '@mui/icons-material';
import { useGetVantagens } from './hooks/useGetVantagem';
import { useCreateVantagem } from './hooks/useCreateVantagem';
import { useUpdateVantagem } from './hooks/useUpdateVantagem';
import { useDeleteVantagem } from './hooks/useDeleteVantagem';
import { useResgatarVantagem } from './hooks/useResgatarVantagem';
import { useAuth } from '../../shared/context/AuthContext';
import { useGetBalance } from '../../pages/transactions/hooks/useTransactions';
import type { Vantagem } from '../../shared/service/vantagemService';
import Header from '../../shared/components/Header';

export default function VantagemCRUD() {
  const { empresaId } = useParams<{ empresaId: string }>(); 
  const { user } = useAuth();
  const [openDialog, setOpenDialog] = useState(false);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [openResgatoDialog, setOpenResgatoDialog] = useState(false);
  const [openConfirmResgatoDialog, setOpenConfirmResgatoDialog] = useState(false);
  const [resgatoMensagem, setResgatoMensagem] = useState('');
  const [regatoCupom, setRegatoCupom] = useState('');
  const [vantagemToDelete, setVantagemToDelete] = useState<string | null>(null);
  const [vantagemToResgatar, setVantagemToResgatar] = useState<Vantagem | null>(null);
  const [editingVantagem, setEditingVantagem] = useState<Vantagem | null>(null);
  const [formData, setFormData] = useState({
    descricao: '',
    foto: '',
    custo: 0,
  });
  const [error, setError] = useState('');

  // Determinar o tipo de usuário baseado nos dados armazenados no localStorage
  const userDataStr = localStorage.getItem('userData');
  const userData = userDataStr ? JSON.parse(userDataStr) : null;
  const isEmpresa = userData?.cnpj !== undefined && userData?.cnpj !== null;

  // Para EMPRESA: usa empresaId da URL e chama getVantagens(empresaId)
  // Para ALUNO: chama getAllVantagens() sem empresaId
  const { vantagens, isLoading, isError, error: fetchError, refetch } = useGetVantagens(
    isEmpresa ? empresaId : undefined
  );
  const { balance } = useGetBalance(user?.id);
  const { resgatar, isResgatando } = useResgatarVantagem(user?.id);
  const { createVantagem, isLoading: isCreating, isSuccess: createSuccess } = useCreateVantagem();
  const { updateVantagem, isLoading: isUpdating, isSuccess: updateSuccess } = useUpdateVantagem();
  const { deleteVantagem, isLoading: isDeleting } = useDeleteVantagem();

  useEffect(() => {
    if (createSuccess || updateSuccess) {
      handleCloseDialog();
      refetch();
    }
  }, [createSuccess, updateSuccess, refetch]);

  const handleOpenDialog = (vantagem?: Vantagem) => {
    if (vantagem) {
      setEditingVantagem(vantagem);
      setFormData({
        descricao: vantagem.descricao,
        foto: vantagem.foto || '',
        custo: vantagem.custo,
      });
    } else {
      setEditingVantagem(null);
      setFormData({
        descricao: '',
        foto: '',
        custo: 0,
      });
    }
    setOpenDialog(true);
    setError('');
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setEditingVantagem(null);
    setFormData({
      descricao: '',
      foto: '',
      custo: 0,
    });
    setError('');
  };

  const handleSubmit = () => {
    if (!isEmpresa || !empresaId) {
      setError('Apenas empresas podem gerenciar vantagens');
      return;
    }

    if (!formData.descricao || formData.custo <= 0) {
      setError('Por favor, preencha todos os campos obrigatórios');
      return;
    }

    if (editingVantagem) {
      updateVantagem({
        empresaId,
        vantagemId: editingVantagem.id,
        vantagemData: formData,
      });
    } else {
      createVantagem({
        empresaId,
        vantagemData: formData,
      });
    }
  };

  const handleOpenDeleteDialog = (vantagemId: string) => {
    setVantagemToDelete(vantagemId);
    setOpenDeleteDialog(true);
  };

  const handleCloseDeleteDialog = () => {
    setOpenDeleteDialog(false);
    setVantagemToDelete(null);
  };

  const handleConfirmDelete = () => {
    if (vantagemToDelete && empresaId) {
      deleteVantagem({ empresaId, vantagemId: vantagemToDelete });
      handleCloseDeleteDialog();
    }
  };

  const handleOpenConfirmResgato = (vantagem: Vantagem) => {
    setVantagemToResgatar(vantagem);
    setOpenConfirmResgatoDialog(true);
  };

  const handleCloseConfirmResgato = () => {
    setOpenConfirmResgatoDialog(false);
    setVantagemToResgatar(null);
  };

  const handleConfirmResgato = () => {
    if (!vantagemToResgatar) return;

    // Verificar se o usuário tem saldo suficiente
    if (balance < vantagemToResgatar.custo) {
      setError(`Saldo insuficiente. Você tem ${balance} moedas e precisa de ${vantagemToResgatar.custo}`);
      handleCloseConfirmResgato();
      return;
    }

    // Realizar o resgate
    resgatar(vantagemToResgatar.id, {
      onSuccess: (data) => {
        setRegatoCupom(data.cupom);
        setResgatoMensagem(data.mensagem || 'Vantagem resgatada com sucesso!');
        setOpenResgatoDialog(true);
        handleCloseConfirmResgato();
        refetch();
      },
      onError: (error: any) => {
        setError(error?.message || 'Erro ao resgatar vantagem');
        handleCloseConfirmResgato();
      },
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
        <Alert severity="error" sx={{ mt: 4 }}>
          Erro ao carregar vantagens: {fetchError?.message}
        </Alert>
      </Container>
    );
  }

  return (
    <Box sx={{ bgcolor: '#1a1a1a', minHeight: '100vh' }}>
      <Header />
      <Container maxWidth="lg" sx={{ py: 4 }}>
        {isEmpresa && (
          <Box sx={{ mb: 4, display: 'flex', justifyContent: 'flex-end' }}>
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={() => handleOpenDialog()}
              size="large"
              sx={{
                bgcolor: '#ff6b6b',
                '&:hover': {
                  bgcolor: '#ff5252',
                },
              }}
            >
              Nova Vantagem
            </Button>
          </Box>
        )}

        {vantagens.length === 0 ? (
          <Alert severity="info" sx={{ bgcolor: '#3d3d3d', color: '#e0e0e0', border: '1px solid #555' }}>
            {isEmpresa 
              ? 'Nenhuma vantagem cadastrada. Clique em "Nova Vantagem" para criar uma.'
              : 'Nenhuma vantagem disponível no momento.'
            }
          </Alert>
        ) : (
          <Grid container spacing={3}>
            {vantagens.map((vantagem) => (
              <Grid size={{ xs: 12, sm: 6, md: 4 }} key={vantagem.id}>
                <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column', bgcolor: '#2d2d2d', color: '#e0e0e0' }}>
                  {vantagem.foto ? (
                    <CardMedia
                      component="img"
                      height="200"
                      image={vantagem.foto}
                      alt={vantagem.descricao}
                      sx={{ objectFit: 'cover' }}
                    />
                  ) : (
                    <Box
                      component="img"
                      src="https://images.unsplash.com/photo-1518546305927-30cf4ba20d7f?w=400&h=200&fit=crop&q=80"
                      alt="Bitcoin"
                      sx={{
                        height: 200,
                        width: '100%',
                        objectFit: 'cover',
                        backgroundColor: '#1a1a1a',
                      }}
                      onError={(e) => {
                        const img = e.currentTarget as HTMLImageElement;
                        img.style.display = 'none';
                        if (img.nextElementSibling) {
                          (img.nextElementSibling as HTMLElement).style.display = 'flex';
                        }
                      }}
                    />
                  )}
                  {!vantagem.foto && (
                    <Box
                      sx={{
                        height: 200,
                        bgcolor: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                        display: 'none',
                        alignItems: 'center',
                        justifyContent: 'center',
                      }}
                    >
                      <VoucherIcon sx={{ fontSize: 80, color: 'white', opacity: 0.5 }} />
                    </Box>
                  )}
                  <CardContent sx={{ flexGrow: 1 }}>
                    <Typography variant="body1" sx={{ mb: 2, color: '#b0b0b0' }}>
                      {vantagem.descricao}
                    </Typography>
                    <Chip
                      label={`${vantagem.custo} moedas`}
                      size="small"
                      sx={{
                        bgcolor: '#463a4dff',
                        color: '#f0f0f0',
                        fontWeight: 600,
                        border: '2px solid #463a4dff',
                      }}
                    />
                  </CardContent>
                  {isEmpresa && (
                    <CardActions sx={{ justifyContent: 'flex-end', p: 2 }}>
                      <IconButton
                        size="small"
                        onClick={() => handleOpenDialog(vantagem)}
                        sx={{
                          color: '#6C3751',
                          '&:hover': {
                            bgcolor: 'rgba(108, 55, 81, 0.1)',
                          },
                        }}
                      >
                        <EditIcon />
                      </IconButton>
                      <IconButton
                        size="small"
                        color="error"
                        onClick={() => handleOpenDeleteDialog(vantagem.id)}
                        disabled={isDeleting}
                      >
                        <DeleteIcon />
                      </IconButton>
                    </CardActions>
                  )}
                  {!isEmpresa && (
                    <CardActions sx={{ justifyContent: 'center', p: 2 }}>
                      <Button
                        variant="contained"
                        size="large"
                        startIcon={<ShoppingCartIcon />}
                        disabled={isResgatando || balance < vantagem.custo}
                        onClick={() => handleOpenConfirmResgato(vantagem)}
                        sx={{
                          bgcolor: balance < vantagem.custo ? '#999' : '#ff6b6b',
                          color: '#fff',
                          fontWeight: 700,
                          borderRadius: '20px',
                          px: 3,
                          py: 1.5,
                          textTransform: 'none',
                          fontSize: '0.95rem',
                          transition: 'all 0.3s ease',
                          '&:hover': {
                            bgcolor: balance < vantagem.custo ? '#999' : '#ff5252',
                            transform: 'scale(1.05)',
                            boxShadow: '0 4px 12px rgba(255, 107, 107, 0.3)',
                          },
                          '&:disabled': {
                            bgcolor: '#999',
                            color: '#ddd',
                          },
                        }}
                      >
                        {balance < vantagem.custo ? 'Saldo Insuficiente' : 'Resgatar'}
                      </Button>
                    </CardActions>
                  )}
                </Card>
              </Grid>
            ))}
          </Grid>
        )}

        <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
          <DialogTitle>
            {editingVantagem ? 'Editar Vantagem' : 'Nova Vantagem'}
          </DialogTitle>
          <DialogContent>
            {error && (
              <Alert severity="error" sx={{ mb: 2 }}>
                {error}
              </Alert>
            )}
            <TextField
              fullWidth
              label="Descrição *"
              value={formData.descricao}
              onChange={(e) => setFormData({ ...formData, descricao: e.target.value })}
              margin="normal"
              multiline
              rows={4}
              placeholder="Descreva os detalhes da vantagem..."
            />
            <TextField
              fullWidth
              label="URL da Foto"
              value={formData.foto}
              onChange={(e) => setFormData({ ...formData, foto: e.target.value })}
              margin="normal"
              placeholder="https://exemplo.com/imagem.jpg"
            />
            <TextField
              fullWidth
              label="Custo em Moedas *"
              type="number"
              value={formData.custo}
              onChange={(e) => setFormData({ ...formData, custo: Number(e.target.value) })}
              margin="normal"
              inputProps={{ min: 1 }}
            />
          </DialogContent>
          <DialogActions sx={{ p: 3 }}>
            <Button onClick={handleCloseDialog} disabled={isCreating || isUpdating}>
              Cancelar
            </Button>
            <Button
              onClick={handleSubmit}
              variant="contained"
              disabled={isCreating || isUpdating}
              sx={{
                bgcolor: '#ff6b6b',
                '&:hover': {
                  bgcolor: '#ff5252',
                },
              }}
            >
              {isCreating || isUpdating ? (
                <>
                  <CircularProgress size={20} sx={{ color: 'white', mr: 1 }} />
                  Salvando...
                </>
              ) : (
                'Salvar'
              )}
            </Button>
          </DialogActions>
        </Dialog>

        <Dialog open={openDeleteDialog} onClose={handleCloseDeleteDialog} maxWidth="xs" fullWidth>
          <DialogTitle>Confirmar Exclusão</DialogTitle>
          <DialogContent>
            <Typography>
              Tem certeza que deseja excluir esta vantagem? Esta ação não pode ser desfeita.
            </Typography>
          </DialogContent>
          <DialogActions sx={{ p: 3 }}>
            <Button onClick={handleCloseDeleteDialog} disabled={isDeleting}>
              Cancelar
            </Button>
            <Button
              onClick={handleConfirmDelete}
              variant="contained"
              color="error"
              disabled={isDeleting}
            >
              {isDeleting ? (
                <>
                  <CircularProgress size={20} sx={{ color: 'white', mr: 1 }} />
                  Excluindo...
                </>
              ) : (
                'Excluir'
              )}
            </Button>
          </DialogActions>
        </Dialog>

        <Dialog open={openConfirmResgatoDialog} onClose={handleCloseConfirmResgato} maxWidth="sm" fullWidth>
          <DialogTitle sx={{ display: 'flex', alignItems: 'center', gap: 1, color: '#6C3751', fontWeight: 600 }}>
            <WarningIcon />
            Confirmar Resgate
          </DialogTitle>
          <DialogContent sx={{ pt: 3 }}>
            {vantagemToResgatar && (
              <Box sx={{ textAlign: 'center' }}>
                <Typography sx={{ mb: 2, fontSize: '0.95rem' }}>
                  Você deseja resgatar a vantagem:
                </Typography>
                <Typography sx={{ fontWeight: 600, fontSize: '1.1rem', mb: 3, color: '#463a4dff' }}>
                  {vantagemToResgatar.descricao}
                </Typography>
                <Box sx={{ display: 'flex', justifyContent: 'space-around', mb: 3 }}>
                  <Box sx={{ textAlign: 'center' }}>
                    <Typography sx={{ fontSize: '0.85rem', color: '#666', mb: 1 }}>
                      Custo
                    </Typography>
                    <Typography sx={{ fontWeight: 700, fontSize: '1.5rem', color: '#6C3751' }}>
                      {vantagemToResgatar.custo}
                    </Typography>
                    <Typography sx={{ fontSize: '0.75rem', color: '#666' }}>
                      moedas
                    </Typography>
                  </Box>
                  <Box sx={{ textAlign: 'center' }}>
                    <Typography sx={{ fontSize: '0.85rem', color: '#666', mb: 1 }}>
                      Seu Saldo
                    </Typography>
                    <Typography sx={{ fontWeight: 700, fontSize: '1.5rem', color: balance >= vantagemToResgatar.custo ? '#4CAF50' : '#f44336' }}>
                      {balance}
                    </Typography>
                    <Typography sx={{ fontSize: '0.75rem', color: '#666' }}>
                      moedas
                    </Typography>
                  </Box>
                </Box>
                {balance >= vantagemToResgatar.custo ? (
                  <Alert severity="success">
                    Você tem saldo suficiente para realizar este resgate.
                  </Alert>
                ) : (
                  <Alert severity="error">
                    Saldo insuficiente! Você precisa de {vantagemToResgatar.custo - balance} moedas a mais.
                  </Alert>
                )}
              </Box>
            )}
          </DialogContent>
          <DialogActions sx={{ p: 3, justifyContent: 'space-between' }}>
            <Button onClick={handleCloseConfirmResgato} disabled={isResgatando}>
              Cancelar
            </Button>
            <Button
              onClick={handleConfirmResgato}
              variant="contained"
              disabled={isResgatando || !vantagemToResgatar || balance < vantagemToResgatar.custo}
              sx={{
                bgcolor: '#ff6b6b',
                '&:hover': {
                  bgcolor: '#ff5252',
                },
              }}
            >
              {isResgatando ? (
                <>
                  <CircularProgress size={20} sx={{ color: 'white', mr: 1 }} />
                  Resgatando...
                </>
              ) : (
                'Confirmar Resgate'
              )}
            </Button>
          </DialogActions>
        </Dialog>

        <Dialog open={openResgatoDialog} onClose={() => setOpenResgatoDialog(false)} maxWidth="sm" fullWidth>
          <DialogTitle sx={{ textAlign: 'center', fontWeight: 600 }}>
            ✓ Cupom de Resgate
          </DialogTitle>
          <DialogContent sx={{ pt: 3, textAlign: 'center' }}>
            <Typography sx={{ mb: 3, fontSize: '0.95rem' }}>
              {resgatoMensagem}
            </Typography>
            <Box
              sx={{
                p: 2,
                bgcolor: '#2d2d2d',
                borderRadius: 1,
                border: '2px solid #f0f0f0',
              }}
            >
              <Typography sx={{ fontSize: '0.75rem', color: '#e0e0e0', mb: 1 }}>
                Código do Cupom
              </Typography>
              <Typography
                sx={{
                  fontSize: '1.5rem',
                  fontWeight: 700,
                  color: '#f0f0f0',
                  fontFamily: 'monospace',
                  letterSpacing: 2,
                }}
              >
                {regatoCupom}
              </Typography>
            </Box>
            <Typography sx={{ mt: 3, fontSize: '0.85rem', color: '#e0e0e0' }}>
              Apresente este código no local indicado para validar seu resgate.
            </Typography>
          </DialogContent>
          <DialogActions sx={{ p: 2, justifyContent: 'center' }}>
            <Button
              onClick={() => setOpenResgatoDialog(false)}
              variant="contained"
              sx={{
                bgcolor: '#f0f0f0',
                color: '#000',
                '&:hover': {
                  bgcolor: '#e0e0e0',
                },
              }}
            >
              Fechar
            </Button>
          </DialogActions>
        </Dialog>
      </Container>
    </Box>
  );
}
