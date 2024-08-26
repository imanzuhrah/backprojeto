/*
 * Copyright 2007 - presente autor(es) original(ais).
 *
 * Licenciado sob a Licença Apache, Versão 2.0 (a "Licença");
 * você não pode usar este arquivo exceto em conformidade com a Licença.
 * Você pode obter uma cópia da Licença em
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * A menos que exigido pela lei aplicável ou acordado por escrito, o software
 * distribuído sob a Licença é distribuído "COMO ESTÁ",
 * SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, expressas ou implícitas.
 * Consulte a Licença para o idioma específico que rege as permissões e
 * limitações sob a Licença.
 */
importar java.net.*;
importar java.io.*;
importar java.nio.channels.*;
importar java.util.Properties;

classe pública MavenWrapperDownloader {

    String final estática privada WRAPPER_VERSION = "0.5.6";
    /**
     * URL padrão para baixar o maven-wrapper.jar, caso nenhum 'downloadUrl' seja fornecido.
     */
    String final estática privada DEFAULT_DOWNLOAD_URL = "https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/"
        + VERSÃO_DO_WRAPPER + "/maven-wrapper-" + VERSÃO_DO_WRAPPER + ".jar";

    /**
     * Caminho para o arquivo maven-wrapper.properties, que pode conter uma propriedade downloadUrl para
     * use em vez do padrão.
     */
    String final estática privada MAVEN_WRAPPER_PROPERTIES_PATH =
            ".mvn/wrapper/maven-wrapper.properties";

    /**
     * Caminho onde o maven-wrapper.jar será salvo.
     */
    String final estática privada MAVEN_WRAPPER_JAR_PATH =
            ".mvn/wrapper/maven-wrapper.jar";

    /**
     * Nome da propriedade que deve ser usada para substituir a URL de download padrão do wrapper.
     */
    String final estática privada NOME_DA_PROPRIEDADE_URL_DO_EMBRADOR = "wrapperUrl";

    público estático vazio main(String args[]) {
        System.out.println("- Downloader iniciado");
        Arquivo baseDirectory = novo arquivo(args[0]);
        System.out.println("- Usando diretório base: " + baseDirectory.getAbsolutePath());

        // Se o maven-wrapper.properties existir, leia-o e verifique se ele contém um personalizado
        // parâmetro wrapperUrl.
        Arquivo mavenWrapperPropertyFile = novo arquivo(baseDirectory, MAVEN_WRAPPER_PROPERTIES_PATH);
        URL da sequência de caracteres = URL_DEFAULT_DOWNLOAD;
        se(mavenWrapperPropertyFile.existe()) {
            FileInputStream mavenWrapperPropertyFileInputStream = null;
            tentar {
                mavenWrapperPropertyFileInputStream = novo FileInputStream(mavenWrapperPropertyFile);
                Propriedades mavenWrapperProperties = new Properties();
                mavenWrapperProperties.load(mavenWrapperPropertyFileInputStream);
                url = mavenWrapperProperties.getProperty(NOME_DA_PROPRIEDADE_URL_DO_WRAPPER, url);
            } catch (IOException e) {
                System.out.println("- ERRO ao carregar '" + MAVEN_WRAPPER_PROPERTIES_PATH + "'");
            } finalmente {
                tentar {
                    se(mavenWrapperPropertyFileInputStream != nulo) {
                        mavenWrapperPropertyFileInputStream.close();
                    }
                } catch (IOException e) {
                    // Ignorar ...
                }
            }
        }
        System.out.println("- Baixando de: " + url);

        Arquivo outputFile = novo arquivo(baseDirectory.getAbsolutePath(), MAVEN_WRAPPER_JAR_PATH);
        se(!outputFile.getParentFile().existe()) {
            se(!outputFile.getParentFile().mkdirs()) {
                Sistema.out.println(
                        "- ERRO ao criar diretório de saída '" + outputFile.getParentFile().getAbsolutePath() + "'");
            }
        }
        System.out.println("- Baixando para: " + outputFile.getAbsolutePath());
        tentar {
            downloadFileFromURL(url, outputFile);
            System.out.println("Concluído");
            Sistema.exit(0);
        } pegar (Throwable e) {
            System.out.println("- Erro ao baixar");
            e.printStackTrace();
            Sistema.exit(1);
        }
    }

    privado estático vazio downloadFileFromURL(String urlString, Arquivo destino) lança exceção {
        se (System.getenv("MVNW_USERNAME") != nulo && System.getenv("MVNW_PASSWORD") != nulo) {
            String nome de usuário = System.getenv("MVNW_USERNAME");
            char[] senha = System.getenv("MVNW_PASSWORD").toCharArray();
            Autenticador.setDefault(novo Autenticador() {
                @Substituir
                autenticação de senha protegida getPasswordAuthentication() {
                    retornar novo PasswordAuthentication(nome de usuário, senha);
                }
            });
        }
        URL do site = nova URL(urlString);
        Canal de Byte Legível rbc;
        rbc = Canais.newChannel(website.openStream());
        FileOutputStream fos = novo FileOutputStream(destino);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.fechar();
        rbc.fechar();
    }

}
