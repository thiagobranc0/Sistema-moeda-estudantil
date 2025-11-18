import { useState } from 'react';
import { useParams } from 'react-router-dom';
import {
  Box,
  Container,
  Typography,
  Card,
  CardContent,
  TextField,
  Button,
  Alert,
  CircularProgress,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import { Send as SendIcon } from '@mui/icons-material';
import { useAuth } from '../../shared/context/AuthContext';
import Header from '../../shared/components/Header';
import { useGetBalance, useSendCoins, useGetSentByProfessor } from '../transactions/hooks/useTransactions';

export default function ProfessorDoar() {
  const { professorId } = useParams<{ professorId: string }>();
  const numProfessorId = Number(professorId);
  const { user } = useAuth();
  const [studentEmail, setStudentEmail] = useState('');
  const [amount, setAmount] = useState('');
  const [message, setMessage] = useState('');
  const [openDialog, setOpenDialog] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [error, setError] = useState('');

  const { balance } = useGetBalance(numProfessorId);
  const { sendCoins, isSending } = useSendCoins(numProfessorId);
  const { sent, isLoading: loadingSent } = useGetSentByProfessor(numProfessorId);

  let tipoNormalizado = (user?.tipo || '').toUpperCase();
  if (tipoNormalizado === 'PROFESSOR') {
    tipoNormalizado = 'PROFESSOR';
  }
  const isProfessor = tipoNormalizado === 'PROFESSOR';

  if (!professorId || !user || !isProfessor) {
    return (
      <Box sx={{ bgcolor: '#1a1a1a', minHeight: '100vh' }}>
        <Header />
        <Container>
          <Alert severity="error" sx={{ mt: 4 }}>
            Acesso restrito. Apenas professores podem acessar esta página.
          </Alert>
        </Container>
      </Box>
    );
  }

  const handleOpenDialog = () => {
    setError('');
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handleSubmit = () => {
    if (!studentEmail || !amount || !message) {
      setError('Todos os campos são obrigatórios');
      return;
    }

    const numAmount = Number(amount);
    if (numAmount <= 0 || numAmount > balance) {
      setError(`Valor inválido. Saldo disponível: ${balance} moedas`);
      return;
    }

    sendCoins(
      { to: studentEmail, amount: numAmount, message },
      {
        onSuccess: () => {
          setSuccessMessage(`${numAmount} moedas enviadas para ${studentEmail}`);
          setStudentEmail('');
          setAmount('');
          setMessage('');
          handleCloseDialog();
          setTimeout(() => setSuccessMessage(''), 5000);
        },
        onError: () => {
          setError('Erro ao enviar moedas. Tente novamente.');
        },
      }
    );
  };

  return (
    <Box sx={{ bgcolor: '#1a1a1a', minHeight: '100vh' }}>
      <Header />
      <Container maxWidth="lg" sx={{ py: 4 }}>
        {successMessage && (
          <Alert severity="success" sx={{ mb: 3, bgcolor: '#2d3a2d', color: '#8FBC8F' }}>
            {successMessage}
          </Alert>
        )}

        <Card sx={{ bgcolor: '#2d2d2d', color: '#e0e0e0', mb: 4 }}>
          <CardContent>
            <Typography variant="h5" sx={{ color: '#f0f0f0', fontWeight: 600, mb: 2 }}>
              Seu Saldo
            </Typography>
            <Typography variant="h3" sx={{ color: '#f0f0f0', fontWeight: 700 }}>
              {balance} moedas
            </Typography>
          </CardContent>
        </Card>

        <Card sx={{ bgcolor: '#2d2d2d', color: '#e0e0e0', mb: 4 }}>
          <CardContent>
            <Typography variant="h5" sx={{ color: '#f0f0f0', fontWeight: 600, mb: 3 }}>
              Enviar Moedas
            </Typography>

            {error && (
              <Alert severity="error" sx={{ mb: 2, bgcolor: '#3d2020', color: '#ff6b6b' }}>
                {error}
              </Alert>
            )}

            <TextField
              fullWidth
              label="Email do Aluno"
              type="email"
              value={studentEmail}
              onChange={(e) => setStudentEmail(e.target.value)}
              margin="normal"
              placeholder="aluno@example.com"
              sx={{
                '& .MuiOutlinedInput-root': {
                  color: '#e0e0e0',
                  '& fieldset': { borderColor: '#4a4a4a' },
                  '&:hover fieldset': { borderColor: '#f0f0f0' },
                  '&.Mui-focused fieldset': { borderColor: '#f0f0f0' },
                },
                '& .MuiInputLabel-root': { color: '#b0b0b0' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#f0f0f0' },
              }}
            />

            <TextField
              fullWidth
              label="Quantidade de Moedas"
              type="number"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              margin="normal"
              inputProps={{ min: 1, max: balance }}
              sx={{
                '& .MuiOutlinedInput-root': {
                  color: '#e0e0e0',
                  '& fieldset': { borderColor: '#4a4a4a' },
                  '&:hover fieldset': { borderColor: '#f0f0f0' },
                  '&.Mui-focused fieldset': { borderColor: '#f0f0f0' },
                },
                '& .MuiInputLabel-root': { color: '#b0b0b0' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#f0f0f0' },
              }}
            />

            <TextField
              fullWidth
              label="Motivo (Mensagem)"
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              margin="normal"
              multiline
              rows={4}
              placeholder="Ex: Excelente participação em aula..."
              sx={{
                '& .MuiOutlinedInput-root': {
                  color: '#e0e0e0',
                  '& fieldset': { borderColor: '#4a4a4a' },
                  '&:hover fieldset': { borderColor: '#f0f0f0' },
                  '&.Mui-focused fieldset': { borderColor: '#f0f0f0' },
                },
                '& .MuiInputLabel-root': { color: '#b0b0b0' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#f0f0f0' },
              }}
            />

            <Button
              variant="contained"
              startIcon={<SendIcon />}
              onClick={handleOpenDialog}
              disabled={isSending || balance === 0}
              sx={{
                mt: 3,
                bgcolor: '#ff6b6b',
                color: '#fff',
                fontWeight: 600,
                '&:hover': { bgcolor: '#ff5252' },
              }}
            >
              Enviar Moedas
            </Button>
          </CardContent>
        </Card>

        <Card sx={{ bgcolor: '#2d2d2d', color: '#e0e0e0' }}>
          <CardContent>
            <Typography variant="h5" sx={{ color: '#f0f0f0', fontWeight: 600, mb: 3 }}>
              Moedas Enviadas ({sent.length})
            </Typography>

            {loadingSent ? (
              <CircularProgress sx={{ color: '#f0f0f0' }} />
            ) : sent.length === 0 ? (
              <Typography sx={{ color: '#b0b0b0' }}>Nenhuma moeda enviada ainda.</Typography>
            ) : (
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                {sent.map((t) => (
                  <Box
                    key={t.id}
                    sx={{
                      p: 2,
                      bgcolor: '#252525',
                      borderRadius: 1,
                      borderLeft: '4px solid #f0f0f0',
                    }}
                  >
                    <Typography sx={{ color: '#e0e0e0', fontWeight: 600 }}>
                      +{t.quantidade} moedas para aluno {t.alunoId}
                    </Typography>
                    <Typography sx={{ color: '#b0b0b0', fontSize: '0.9rem' }}>
                      {t.motivo || 'Sem motivo'}
                    </Typography>
                    <Typography sx={{ color: '#f0f0f0', fontSize: '0.8rem', mt: 1 }}>
                      {t.dataCriacao ? new Date(t.dataCriacao).toLocaleDateString('pt-BR') : '-'}
                    </Typography>
                  </Box>
                ))}
              </Box>
            )}
          </CardContent>
        </Card>

        <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="xs" fullWidth>
          <DialogTitle sx={{ color: '#f0f0f0', fontWeight: 600 }}>Confirmar Envio</DialogTitle>
          <DialogContent sx={{ bgcolor: '#2d2d2d', color: '#e0e0e0' }}>
            <Typography sx={{ mt: 2 }}>
              Enviar <strong>{amount}</strong> moedas para <strong>{studentEmail}</strong>?
            </Typography>
          </DialogContent>
          <DialogActions sx={{ bgcolor: '#252525', p: 2 }}>
            <Button onClick={handleCloseDialog} sx={{ color: '#b0b0b0' }}>
              Cancelar
            </Button>
            <Button
              onClick={handleSubmit}
              variant="contained"
              disabled={isSending}
              sx={{
                bgcolor: '#ff6b6b',
                color: '#fff',
                '&:hover': { bgcolor: '#ff5252' },
              }}
            >
              {isSending ? <CircularProgress size={20} /> : 'Confirmar'}
            </Button>
          </DialogActions>
        </Dialog>
      </Container>
    </Box>
  );
}
