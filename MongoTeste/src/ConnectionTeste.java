import java.net.UnknownHostException;
import java.util.Scanner;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConnectionTeste {

	private static MongoClientURI uri; // ir� conter a URL do banco de dados, e das tabelas
	private static MongoClient mongo; // servir� para podermos acessar o banco de dados, e as tabelas
	private static DB database; // ir� guardar o banco de dados
	private static DBCollection collection; // ir� guardar a tabela

	// DB / DataBase / Banco de Dados = mesma coisa
	// Collection / Tabela = mesma coisa
	// document / documento = coisas que est�o dentro das tabelas
	
	public static void main(String[] args) throws Exception {
		try {
		
			// ficar� repetindo at� que escrevam 0
			while(true == true) {
				
				connect(); // Abre a conexao para acessarmos as tabelas e o banco
				
				System.out.println("\n\n1-INSERIR \n2-ATUALIZAR \n3-DELETAR \n4-BUSCAR TODOS \n5-BUSCAR UM\n\n");
				Scanner sc = new Scanner(System.in);
				int opcao = sc.nextInt();

				
				switch (opcao) {
				
				case 0:
					
					disconnect(); // Fecha a conexao com o banco
					System.exit(0); // Fecha o programa
					
					break;
				
				case 1:

					// Aqui ele insere um "documento" na tabela
					
					System.out.println("Digite alguma coisa:");
					String strDocument_Insert = sc.next();
					
					Insert_Document(strDocument_Insert);

					break;

				case 2:

					// Aqui ele atualiza um "documento" ja existente na tabela por um novo
					
					System.out.println("Digite o antigo conteudo:");
					String strOldDocument_Update = sc.next();
					System.out.println("Digite o novo conteudo:");
					String strNewDocument_Update = sc.next();
					
					Update_Document(strNewDocument_Update,strOldDocument_Update);

					break;

				case 3:
					
					// Aqui ele deleta um "documento"

					System.out.println("Digite o que quiser deletar:");
					String strDocument_Delete = sc.next();
					
					Delete_Document(strDocument_Delete);

					break;

				case 4:

					// Aqui ele lista todos os documentos que existem na tabela
					
					Read_Documents();

					break;

				case 5:
					
					// Aqui ele lista um "documento" especifico que existir na tabela
					
					System.out.println("Digite o que estiver procurando:");
					String strDocument_Read = sc.next();

					Read_Document(strDocument_Read);

					break;

				default:
					break;
				}
				
				disconnect();
				
			}

			
		} catch (Exception e) {
			disconnect();
		}
	}

	private static void connect() {
		try {
			
			// Aqui se atribui os valores para as variaveis que se conectam ao banco
			
			uri = new MongoClientURI(
					"mongodb+srv://thales:iambatman@teste-tngy3.mongodb.net/test?retryWrites=true&w=majority");
			
			mongo = new MongoClient(uri);
			database = mongo.getDB("DataBaseTeste");
			collection = database.getCollection("CollectionTeste");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void disconnect() {
		try {
			
			// Desconecta do banco,e limpa as configura��es
			
			mongo.close();
			
			database = mongo.getDB("");
			collection = database.getCollection("");
			uri = new MongoClientURI("");
			mongo = new MongoClient(uri);
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Insere um documento
	public static void Insert_Document(String strDocument) {
		try {

			BasicDBObject document = new BasicDBObject("nome", strDocument);
			collection.insert(document);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Atualiza um documento
	public static void Update_Document(String strNewDocument_Update, String strOldDocument_Update) {
		try {
			// new value
			BasicDBObject newDocument = new BasicDBObject("nome", strNewDocument_Update);
			
			// old value
			BasicDBObject oldDocument = new BasicDBObject("nome", strOldDocument_Update);
			
			collection.update(oldDocument, newDocument);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Deleta um documento
	public static void Delete_Document(String strDocument) {
		try {

			BasicDBObject document = new BasicDBObject("nome", strDocument);
			
			collection.remove(document);
			
			//collection.drop(); isso apaga a collection
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Le todos os documentos
	public static void Read_Documents() {
		try {

			Cursor cursor = collection.find();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());

			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Le um documento
	public static void Read_Document(String strDocument) {
		try {

			BasicDBObject document = new BasicDBObject("nome",strDocument);
			
			Cursor cursor = collection.find(document);
			while (cursor.hasNext()) {
				System.out.println(cursor.next());

			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
