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
} from '@mui/icons-material';
import { useGetVantagens } from './hooks/useGetVantagem';
import { useCreateVantagem } from './hooks/useCreateVantagem';
import { useUpdateVantagem } from './hooks/useUpdateVantagem';
import { useDeleteVantagem } from './hooks/useDeleteVantagem';
import type { Vantagem } from '../../shared/service/vantagemService';
import Header from '../../shared/components/Header';

export default function VantagemCRUD() {
  const { empresaId } = useParams<{ empresaId: string }>(); 
  const [openDialog, setOpenDialog] = useState(false);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [vantagemToDelete, setVantagemToDelete] = useState<string | null>(null);
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
    <Box sx={{ bgcolor: '#f5f5f5', minHeight: '100vh' }}>
      <Header title={isEmpresa ? "Gerenciar Vantagens" : "Vantagens Disponíveis"} />
      <Container maxWidth="lg" sx={{ py: 4 }}>
        {isEmpresa && (
          <Box sx={{ mb: 4, display: 'flex', justifyContent: 'flex-end' }}>
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={() => handleOpenDialog()}
              size="large"
              sx={{
                bgcolor: '#6C3751',
                '&:hover': {
                  bgcolor: '#52223C',
                },
              }}
            >
              Nova Vantagem
            </Button>
          </Box>
        )}

        {vantagens.length === 0 ? (
          <Alert severity="info">
            {isEmpresa 
              ? 'Nenhuma vantagem cadastrada. Clique em "Nova Vantagem" para criar uma.'
              : 'Nenhuma vantagem disponível no momento.'
            }
          </Alert>
        ) : (
          <Grid container spacing={3}>
            {vantagens.map((vantagem) => (
              <Grid size={{ xs: 12, sm: 6, md: 4 }} key={vantagem.id}>
                <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
                  {vantagem.foto && (
                    <CardMedia
                      component="img"
                      height="200"
                      image={vantagem.foto}
                      alt={vantagem.descricao}
                      sx={{ objectFit: 'cover' }}
                    />
                  )}
                  {!vantagem.foto && (
                    <Box
                      sx={{
                        height: 200,
                        bgcolor: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                      }}
                    >
                      <VoucherIcon sx={{ fontSize: 80, color: 'white', opacity: 0.5 }} />
                    </Box>
                  )}
                  <CardContent sx={{ flexGrow: 1 }}>
                    <Typography variant="body1" color="text.secondary" sx={{ mb: 2 }}>
                      {vantagem.descricao}
                    </Typography>
                    <Chip
                      label={`${vantagem.custo} moedas`}
                      size="small"
                      sx={{
                        bgcolor: '#463a4dff',
                        color: 'white',
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
                bgcolor: '#6C3751',
                '&:hover': {
                  bgcolor: '#52223C',
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
      </Container>
    </Box>
  );
}
