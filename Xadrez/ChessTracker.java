import java.io.*;
import java.util.*;

class Movimento {
    String jogada;
    int linhaOrigem, colOrigem;
    int linhaDestino, colDestino;
    
    public Movimento(String jogada, int lOrigem, int cOrigem, int lDestino, int cDestino) {
        this.jogada = jogada;
        this.linhaOrigem = lOrigem;
        this.colOrigem = cOrigem;
        this.linhaDestino = lDestino;
        this.colDestino = cDestino;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %c%d -> %c%d", 
            jogada,
            (char)('a' + colOrigem), 8 - linhaOrigem,
            (char)('a' + colDestino), 8 - linhaDestino);
    }
}

class NoPeca implements Comparable<NoPeca> {
    String id;
    char tipo;
    char cor;
    int linhaAtual, colAtual;
    List<Movimento> historico;
    NoPeca esq, dir;
    
    public NoPeca(String id, char tipo, char cor, int linha, int col) {
        this.id = id;
        this.tipo = tipo;
        this.cor = cor;
        this.linhaAtual = linha;
        this.colAtual = col;
        this.historico = new ArrayList<>();
        this.esq = null;
        this.dir = null;
    }
    
    public void adicionarMovimento(String jogada, int lOrigem, int cOrigem, int lDestino, int cDestino) {
        historico.add(new Movimento(jogada, lOrigem, cOrigem, lDestino, cDestino));
    }
    
    @Override
    public int compareTo(NoPeca outro) {
        return this.id.compareTo(outro.id);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n=== Peça: %s (Tipo: %c, Cor: %c) ===\n", id, tipo, cor));
        sb.append(String.format("Posição atual: %c%d\n", 
            (char)('a' + colAtual), 8 - linhaAtual));
        
        if (!historico.isEmpty()) {
            sb.append("Histórico de movimentos:\n");
            for (int i = 0; i < historico.size(); i++) {
                sb.append(String.format("  %d. %s\n", i + 1, historico.get(i)));
            }
        } else {
            sb.append("Nenhum movimento registrado.\n");
        }
        return sb.toString();
    }
}

class ArvoreBST {
    private NoPeca raiz;
    
    public void inserir(NoPeca peca) {
        raiz = inserirRecursivo(raiz, peca);
    }
    
    private NoPeca inserirRecursivo(NoPeca raiz, NoPeca peca) {
        if (raiz == null) return peca;
        
        int cmp = peca.compareTo(raiz);
        if (cmp < 0) {
            raiz.esq = inserirRecursivo(raiz.esq, peca);
        } else if (cmp > 0) {
            raiz.dir = inserirRecursivo(raiz.dir, peca);
        }
        return raiz;
    }
    
    public NoPeca buscar(String id) {
        return buscarRecursivo(raiz, id);
    }
    
    private NoPeca buscarRecursivo(NoPeca raiz, String id) {
        if (raiz == null) return null;
        
        int cmp = id.compareTo(raiz.id);
        if (cmp == 0) return raiz;
        else if (cmp < 0) return buscarRecursivo(raiz.esq, id);
        else return buscarRecursivo(raiz.dir, id);
    }
    
    public void imprimirInOrder() {
        imprimirInOrderRecursivo(raiz);
    }
    
    private void imprimirInOrderRecursivo(NoPeca raiz) {
        if (raiz == null) return;
        imprimirInOrderRecursivo(raiz.esq);
        System.out.print(raiz);
        imprimirInOrderRecursivo(raiz.dir);
    }
}

class Jogo {
    private NoPeca[][] tabuleiro;
    private ArvoreBST arvore;
    private int numPecasBrancas;
    private int numPecasPretas;
    
    public Jogo() {
        tabuleiro = new NoPeca[8][8];
        arvore = new ArvoreBST();
        numPecasBrancas = 0;
        numPecasPretas = 0;
        inicializarTabuleiro();
    }
    
    private void inicializarTabuleiro() {
        char[] tipos = {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};
        String[] nomes = {"Torre", "Cavalo", "Bispo", "Dama", "Rei", "Bispo", "Cavalo", "Torre"};
        
        for (int i = 0; i < 8; i++) {
            NoPeca peaoBranco = new NoPeca(
                String.format("Peao_B_%d%d", 6, i), 'P', 'B', 6, i);
            tabuleiro[6][i] = peaoBranco;
            arvore.inserir(peaoBranco);
            numPecasBrancas++;
            
            NoPeca peaoPreto = new NoPeca(
                String.format("Peao_P_%d%d", 1, i), 'P', 'P', 1, i);
            tabuleiro[1][i] = peaoPreto;
            arvore.inserir(peaoPreto);
            numPecasPretas++;
        }
        
        for (int i = 0; i < 8; i++) {
            NoPeca pecaBranca = new NoPeca(
                String.format("%s_B_%d%d", nomes[i], 7, i), tipos[i], 'B', 7, i);
            tabuleiro[7][i] = pecaBranca;
            arvore.inserir(pecaBranca);
            numPecasBrancas++;
            
            NoPeca pecaPreta = new NoPeca(
                String.format("%s_P_%d%d", nomes[i], 0, i), tipos[i], 'P', 0, i);
            tabuleiro[0][i] = pecaPreta;
            arvore.inserir(pecaPreta);
            numPecasPretas++;
        }
    }
    
    private String limparJogada(String jogada) {
        return jogada.replaceAll("[+#!?]", "");
    }
    
    private int[] parseDestino(String jogada) {
        int len = jogada.length();
        if (len < 2) return null;
        
        char colChar = jogada.charAt(len - 2);
        char linChar = jogada.charAt(len - 1);
        
        if (colChar < 'a' || colChar > 'h') return null;
        if (linChar < '1' || linChar > '8') return null;
        
        return new int[]{8 - (linChar - '0'), colChar - 'a'};
    }
    
    private NoPeca encontrarPeca(char tipo, int linhaDestino, int colDestino, 
                                  char cor, int colOrigemHint, int linhaOrigemHint) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                NoPeca p = tabuleiro[i][j];
                if (p != null && p.tipo == tipo && p.cor == cor) {
                    if (colOrigemHint >= 0 && j != colOrigemHint) continue;
                    if (linhaOrigemHint >= 0 && i != linhaOrigemHint) continue;
                    
                    if (tipo == 'P') {
                        int direcao = (cor == 'B') ? -1 : 1;
                        if (colDestino == j) {
                            if (i + direcao == linhaDestino) return p;
                            if ((i == 6 && cor == 'B') || (i == 1 && cor == 'P')) {
                                if (i + 2 * direcao == linhaDestino) return p;
                            }
                        } else if (Math.abs(colDestino - j) == 1 && i + direcao == linhaDestino) {
                            return p;
                        }
                    } else if (tipo == 'N') {
                        int dl = Math.abs(linhaDestino - i);
                        int dc = Math.abs(colDestino - j);
                        if ((dl == 2 && dc == 1) || (dl == 1 && dc == 2)) return p;
                    } else if (tipo == 'K') {
                        if (Math.abs(linhaDestino - i) <= 1 && Math.abs(colDestino - j) <= 1) return p;
                    } else {
                        return p;
                    }
                }
            }
        }
        return null;
    }
    
    private void processarRoque(String jogada, char cor) {
        int linha = (cor == 'B') ? 7 : 0;
        
        if (jogada.equals("O-O")) {
            NoPeca rei = tabuleiro[linha][4];
            NoPeca torre = tabuleiro[linha][7];
            
            if (rei != null && torre != null) {
                tabuleiro[linha][6] = rei;
                tabuleiro[linha][5] = torre;
                tabuleiro[linha][4] = null;
                tabuleiro[linha][7] = null;
                
                rei.adicionarMovimento(jogada, linha, 4, linha, 6);
                torre.adicionarMovimento("O-O(Torre)", linha, 7, linha, 5);
                
                rei.linhaAtual = linha;
                rei.colAtual = 6;
                torre.linhaAtual = linha;
                torre.colAtual = 5;
            }
        } else if (jogada.equals("O-O-O")) {
            NoPeca rei = tabuleiro[linha][4];
            NoPeca torre = tabuleiro[linha][0];
            
            if (rei != null && torre != null) {
                tabuleiro[linha][2] = rei;
                tabuleiro[linha][3] = torre;
                tabuleiro[linha][4] = null;
                tabuleiro[linha][0] = null;
                
                rei.adicionarMovimento(jogada, linha, 4, linha, 2);
                torre.adicionarMovimento("O-O-O(Torre)", linha, 0, linha, 3);
                
                rei.linhaAtual = linha;
                rei.colAtual = 2;
                torre.linhaAtual = linha;
                torre.colAtual = 3;
            }
        }
    }
    
    private void processarMovimento(String jogada, char cor) {
        jogada = limparJogada(jogada);
        
        if (jogada.equals("O-O") || jogada.equals("O-O-O")) {
            processarRoque(jogada, cor);
            return;
        }
        
        int[] destino = parseDestino(jogada);
        if (destino == null) return;
        
        int linhaDestino = destino[0];
        int colDestino = destino[1];
        
        char tipo = 'P';
        int idx = 0;
        if (Character.isUpperCase(jogada.charAt(0))) {
            tipo = jogada.charAt(0);
            idx = 1;
        }
        
        int colOrigemHint = -1;
        int linhaOrigemHint = -1;
        
        if (idx < jogada.length() && jogada.charAt(idx) >= 'a' && 
            jogada.charAt(idx) <= 'h' && !jogada.substring(idx).contains("=")) {
            colOrigemHint = jogada.charAt(idx) - 'a';
            idx++;
        }
        if (idx < jogada.length() && jogada.charAt(idx) >= '1' && jogada.charAt(idx) <= '8') {
            linhaOrigemHint = 8 - (jogada.charAt(idx) - '0');
            idx++;
        }
        
        if (idx < jogada.length() && jogada.charAt(idx) == 'x') idx++;
        
        NoPeca peca = encontrarPeca(tipo, linhaDestino, colDestino, cor, colOrigemHint, linhaOrigemHint);
        
        if (peca != null) {
            int linhaOrigem = peca.linhaAtual;
            int colOrigem = peca.colAtual;
            
            NoPeca capturada = tabuleiro[linhaDestino][colDestino];
            if (capturada != null) {
                if (capturada.cor == 'B') numPecasBrancas--;
                else numPecasPretas--;
            }
            
            tabuleiro[linhaOrigem][colOrigem] = null;
            tabuleiro[linhaDestino][colDestino] = peca;
            
            peca.adicionarMovimento(jogada, linhaOrigem, colOrigem, linhaDestino, colDestino);
            
            peca.linhaAtual = linhaDestino;
            peca.colAtual = colDestino;
            
            if (tipo == 'P' && (linhaDestino == 0 || linhaDestino == 7)) {
                int idxPromocao = jogada.indexOf('=');
                if (idxPromocao >= 0 && idxPromocao + 1 < jogada.length()) {
                    peca.tipo = jogada.charAt(idxPromocao + 1);
                } else {
                    peca.tipo = 'Q';
                }
            }
        }
    }
    
    public void processarPGN(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean vezBrancas = true;
            
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("[") || linha.trim().length() < 3) continue;
                
                String[] tokens = linha.split("\\s+");
                for (String token : tokens) {
                    if (token.matches("\\d+\\..*")) continue;
                    if (token.matches("1-0|0-1|1/2-1/2|\\*")) break;
                    
                    if (token.trim().isEmpty()) continue;
                    
                    char cor = vezBrancas ? 'B' : 'P';
                    processarMovimento(token, cor);
                    vezBrancas = !vezBrancas;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }
    
    public void imprimirHistorico() {
        System.out.println("\n========== HISTÓRICO COMPLETO ==========");
        arvore.imprimirInOrder();
    }
    
    public void exibirEstatisticas() {
        System.out.println("\n========== ESTATÍSTICAS DA PARTIDA ==========");
        System.out.println("Peças brancas restantes: " + numPecasBrancas);
        System.out.println("Peças pretas restantes: " + numPecasPretas);
        System.out.println("=============================================");
    }
}

public class ChessTracker {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        
        System.out.println("Processando arquivo PGN...");
        jogo.processarPGN("partida1.pgn");
        
        jogo.imprimirHistorico();
        jogo.exibirEstatisticas();
    }
}