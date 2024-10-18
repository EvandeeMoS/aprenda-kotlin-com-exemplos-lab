// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)

enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

data class Usuario(val nome: String)

data class ConteudoEducacional(val nome: String, val duracaoEmMinutos: Int, val nivel: Nivel)

data class Formacao(val nome: String, val conteudos: MutableList<ConteudoEducacional>) {

    val inscritos = mutableListOf<Usuario>()
    var dificuldadeMedia: Nivel = calcularDificuldadeMedia()

    fun matricular(usuario: Usuario) {
        inscritos.add(usuario)
    }

    fun matricularVarios(vararg usuarios: Usuario) {
        for (usuario in usuarios) {
            inscritos.add(usuario)
        }
    }

    fun adicionarConteudo(conteudo: ConteudoEducacional) {
        conteudos.add(conteudo)
        dificuldadeMedia = calcularDificuldadeMedia()
    }

    fun adicionarVariosConteudos(vararg listaConteudos: ConteudoEducacional) {
        for (conteudo in listaConteudos) {
            conteudos.add(conteudo)
        }
        dificuldadeMedia = calcularDificuldadeMedia()
    }

    private fun calcularDificuldadeMedia(): Nivel {
        return conteudos.let {
            val dificuldadeMap: MutableMap<Nivel, Int> = mutableMapOf(
                Nivel.BASICO to 0,
                Nivel.INTERMEDIARIO to 0,
                Nivel.DIFICIL to 0
            )
            for (conteudo in it) {
                dificuldadeMap[conteudo.nivel] = dificuldadeMap.getValue(conteudo.nivel) + 1
            }
            val quantidadeBasico = dificuldadeMap[Nivel.BASICO]!!
            val quantidadeIntermediario = dificuldadeMap[Nivel.INTERMEDIARIO]!!
            val quantidadeDificil = dificuldadeMap[Nivel.DIFICIL]!!
            val divisor = (quantidadeBasico + quantidadeIntermediario + quantidadeDificil).let { sum ->
                when (sum) {
                    0 -> 1
                    else -> sum
                }
            }
            val result = (1 * quantidadeBasico + 2 * quantidadeIntermediario + 3 * quantidadeDificil) / divisor
            when {
                result <= 1 -> Nivel.BASICO
                result <= 2 -> Nivel.INTERMEDIARIO
                else -> Nivel.DIFICIL
            }
        }
    }
}

data class FormacaoDTO(
    val nome: String,
    val conteudos: List<ConteudoEducacional>,
    val dificuldadeMedia: Nivel,
    val inscritos: List<Usuario>) {
    constructor(formacao: Formacao) : this(formacao.nome, formacao.conteudos, formacao.dificuldadeMedia, formacao.inscritos)
}

fun main() {
    val aula1 = ConteudoEducacional("Introdução a cálculo aplicado a programação", 30, Nivel.BASICO)
    val aula2 = ConteudoEducacional("As bases do cálculo", 60, Nivel.INTERMEDIARIO)

    val formacao1 = Formacao("Cálculo aplicado a programação", mutableListOf())
    formacao1.adicionarVariosConteudos(aula1, aula2)

    // Esperado o output da formação e seus conteúdos adicionados
    /* Output esperado:
        Formacao(
          nome=Cálculo aplicado a programação,
          conteudos=[
              ConteudoEducacional(
                  nome=Introdução a cálculo aplicado a programação,
                  duracaoEmMinutos=30,
                  nivel=BASICO),
              ConteudoEducacional(
                  nome=As bases do cálculo,
                 duracaoEmMinutos=60,
                 nivel=INTERMEDIARIO)
          ]
        ) */
    println(formacao1)

    val pedro = Usuario("Pedro")
    val maria = Usuario("Maria")

    val aula3 = ConteudoEducacional("Limite a fundo", 60, Nivel.DIFICIL)

    formacao1.adicionarConteudo(aula3)
    formacao1.matricular(pedro)
    formacao1.matricular(maria)

    // Esperado o output da formação com o novo conteúdo adicionado seguido do output dos inscritos na formação e com a dificuldade atualizada
    /* Output esperado:
        Formacao(
            nome=Cálculo aplicado a programação,
            conteudos=[
                ConteudoEducacional(
                  nome=Introdução a cálculo aplicado a programação,
                  duracaoEmMinutos=30,
                  nivel=BASICO),
              ConteudoEducacional(
                  nome=As bases do cálculo,
                 duracaoEmMinutos=60,
                 nivel=INTERMEDIARIO),
                ConteudoEducacional(
                    nome=Limite a fundo,
                    duracaoEmMinutos=60,
                    nivel=DIFICIL)
            ]
        )
        [
            Usuario(nome=Pedro),
            Usuario(nome=Maria)
        ]
        INTERMEDIARIO
        */
    println(formacao1)
    println(formacao1.inscritos)
    println(formacao1.dificuldadeMedia)

    // Uso de um DTO para tornar o output único
    val formacaoDto = FormacaoDTO(formacao1)
    println(formacaoDto)
}
