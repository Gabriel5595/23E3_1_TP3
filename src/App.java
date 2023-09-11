import java.util.ArrayList;
import java.util.Scanner;

interface Consumo
{
    double calcular();
}

abstract class LinhaTelefonica implements Consumo
{
    //Variáveis
    private String numero;
    private Cliente cliente;
    private int qtdMinutosGastos;

    //Método construtor
    public LinhaTelefonica(String numero, Cliente cliente, int qtdMinutosGastos)
    {
        this.numero = numero;
        this.cliente = cliente;
        setQtdMinutosGastos(qtdMinutosGastos);
    }

    //Get e Set - numero
    public void setNumero(String numero)
    {
        this.numero = numero;
    }
    public String getNumero()
    {
        return numero;
    }
    
    //Get e Set - Cliente
    public void setCliente(Cliente cliente)
    {
        this.cliente = cliente;
    }
    public Cliente getCliente()
    {
        return cliente;
    }

    //Get e Set - Quantidade de Minutos Gastos
    public void setQtdMinutosGastos(int qtdMinutosGastos)
    {
        if (qtdMinutosGastos >= 0)
        {
            this.qtdMinutosGastos = qtdMinutosGastos;
        } else {
            System.out.println("A quantidade de minutos gastos deve ser superior ou igual a 0.");
        }
    }
    public int getQtdMinutosGastos()
    {
        return qtdMinutosGastos;
    }

    //Método abstrato herdado de Consumo e passado a diante para as classes filhas.
    public abstract double calcular();

    public String toString()
    {
        return "Número: " + numero + "\nCliente: " + cliente.getNome();
    }
}

class Cliente
{
    // Como cada cliente tem que ter no mínimo uma linha telefonica e um máximo ilimitado, a variável "linhas" foi criada para conter uma lista de linhas.
    private String nome;
    private String endereco;
    private ArrayList<LinhaTelefonica> linhas;

    //Método construtor
    public Cliente(String nome, String endereco)
    {
        this.nome = nome;
        this.linhas = new ArrayList<>();
        this.endereco = endereco;
    }

    //Set e Get - Nome
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    //Set e Get - Endereco
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    //Get - Linhas
    public ArrayList<LinhaTelefonica> getLinhas() {
        return linhas;
    }
    
    
    public void adicionarLinha(LinhaTelefonica linha)
    {
        linhas.add(linha);
    }

    public String toString()
    {
        return "Nome do Cliente: " + nome + "\nEndereço: " + endereco;
    }
}

class Fixa extends LinhaTelefonica
{
    private final int FRANQUIA = 30;

    //Método construtor
    public Fixa(String numero, Cliente cliente, int qtdMinutosGastos)
    {
        super(numero, cliente, qtdMinutosGastos);
    }

    public double calcular()
    {
        int minutosExcedentes = Math.max(0, getQtdMinutosGastos() - FRANQUIA);
        return minutosExcedentes * 0.5;
    }

    public String toString()
    {
        return super.toString() + "\nTipo: Fixa\nMinutos Gastos: " + getQtdMinutosGastos();
    }
}

final class Movel extends LinhaTelefonica
{
    private boolean planoDeDadosHabilidato;

    public Movel(String numero, Cliente cliente, int qtdMinutosGastos, boolean planoDeDadosHabilidato)
    {
        super(numero, cliente, qtdMinutosGastos);
        this.planoDeDadosHabilidato = planoDeDadosHabilidato;
    }

    //Set e Get - Plano de dados
    public void setPlanoDeDadosHabilidato(boolean planoDeDadosHabilidato)
    {
        this.planoDeDadosHabilidato = planoDeDadosHabilidato;
    }
    public boolean getPlanoDeDadosHabilidato()
    {
        return planoDeDadosHabilidato;
    }

    public double calcular()
    {
        double custoMinutos = getQtdMinutosGastos() * 0.2;
        if (planoDeDadosHabilidato)
        {
            custoMinutos += 40.00;
        }
        return custoMinutos;
    }

    public String toString() {
        return super.toString() + "\nTipo: Movel\nMinutos Gastos: " + getQtdMinutosGastos() +
                "\nPlano de Dados: " + (planoDeDadosHabilidato ? "Sim" : "Não");
    }
}

public class App
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        Cliente cliente1 = new Cliente("Cliente 1", "Endereço 1");
        Cliente cliente2 = new Cliente("Cliente 2", "Endereço 2");

        System.out.println("Cadastro de Linhas Telefônicas:");

        // Leitura dos dados das linhas fixas
        for (int i = 0; i < 2; i++) {
            System.out.println("Digite o número da linha fixa " + (i + 1) + ":");
            String numero = scanner.nextLine();
            System.out.println("Digite a quantidade de minutos gastos na linha fixa " + (i + 1) + ":");
            int qtdMinutosGastos = Integer.parseInt(scanner.nextLine());
            Fixa linhaFixa = new Fixa(numero, cliente1, qtdMinutosGastos);
            cliente1.adicionarLinha(linhaFixa);
        }

        // Leitura dos dados das linhas móveis
        for (int i = 0; i < 2; i++) {
            System.out.println("Digite o número da linha móvel " + (i + 1) + ":");
            String numero = scanner.nextLine();
            System.out.println("Digite a quantidade de minutos gastos na linha móvel " + (i + 1) + ":");
            int qtdMinutosGastos = Integer.parseInt(scanner.nextLine());
            System.out.println("A linha móvel " + (i + 1) + " possui plano de dados? (Sim/Não):");
            boolean planoDados = scanner.nextLine().equalsIgnoreCase("Sim");
            Movel linhaMovel = new Movel(numero, cliente2, qtdMinutosGastos, planoDados);
            cliente2.adicionarLinha(linhaMovel);
        }

        System.out.println("\nDados dos Clientes e Linhas Telefônicas:");

        System.out.println("\nCliente 1:");
        System.out.println(cliente1);

        for (LinhaTelefonica linha : cliente1.getLinhas()) {
            System.out.println("\n" + linha);
            System.out.println("Custo da linha: R$" + linha.calcular());
        }

        System.out.println("\nCliente 2:");
        System.out.println(cliente2);

        for (LinhaTelefonica linha : cliente2.getLinhas()) {
            System.out.println("\n" + linha);
            System.out.println("Custo da linha: R$" + linha.calcular());
        }

        scanner.close();
    }
}
