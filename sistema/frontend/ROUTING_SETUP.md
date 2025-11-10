# Configuração de Rotas e Redirecionamento

## Mudanças Implementadas

### 1. Instalação do React Router
```bash
npm install react-router-dom
npm install --save-dev @types/react-router-dom
```

### 2. Configuração de Rotas no App.tsx
O aplicativo agora possui as seguintes rotas:
- `/` - Redireciona para `/login`
- `/login` - Página de login
- `/vantagens/:empresaId` - Página de CRUD de vantagens (com ID da empresa na URL)

### 3. Fluxo de Autenticação

#### authService.ts
- Adicionado campo `id` na interface `LoginResponse`
- O ID retornado representa o ID da empresa/usuário logado

#### Login (pages/login/index.tsx)
- Importado `useNavigate` do react-router-dom
- Após login bem-sucedido (200 OK), o usuário é redirecionado para `/vantagens/${data.id}`
- O ID da resposta é usado para construir a URL de redirecionamento

### 4. Página de Vantagens

#### vantagens/index.tsx
- Importado `useParams` do react-router-dom
- O `empresaId` agora vem da URL (não é mais um campo de input)
- Validação: Se não houver `empresaId` na URL, mostra mensagem de erro
- Removido o campo de input manual "ID da Empresa"

### 5. Como Funciona

1. Usuário faz login com email
2. Backend retorna resposta com `id` e outros dados
3. Frontend redireciona para `/vantagens/{id}`
4. Página de vantagens lê o `empresaId` da URL
5. Busca automática das vantagens usando o ID da URL

### 6. Exemplo de Fluxo

```
Login com email → POST /auth/login
                ↓
Response: { id: 123, nome: "...", ... }
                ↓
Redirecionamento → /vantagens/123
                ↓
useParams() → empresaId = "123"
                ↓
GET /empresa/123/vantagens
```

## Próximos Passos Sugeridos

- [ ] Adicionar proteção de rotas (verificar se está autenticado)
- [ ] Salvar dados do usuário em Context API ou localStorage
- [ ] Implementar logout
- [ ] Adicionar página 404
- [ ] Adicionar loading state durante redirecionamento
