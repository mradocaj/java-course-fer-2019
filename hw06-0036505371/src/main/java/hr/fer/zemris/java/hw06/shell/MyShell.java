package hr.fer.zemris.java.hw06.shell;

/**
 * Program koji pokreće shell pomoću kojeg korisnik može obavljati jednostavne radnje sa datotečnim sustavom.
 * Program se pokreće bez argumenata, a iz njega se izlazi upisivanjem "exit".
 * Za pomoć, unijeti "help".
 * 
 * @author Maja Radočaj
 *
 */
public class MyShell {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		System.out.printf("Welcome to MyShell v 1.0\n");
		Environment env = new EnvironmentImpl();
		ShellStatus status = ShellStatus.CONTINUE;

		while(true) {
			try {
				env.write(env.getPromptSymbol() + " ");
				StringBuilder sb = new StringBuilder();
				String line;
				
				do {
					line = env.readLine().trim();
					if(line.endsWith("" + env.getMorelinesSymbol())) {
						sb.append(line.substring(0, line.length() - 1));
						sb.append(" ");
						env.write(env.getMultilineSymbol() + " ");
					} else {
						sb.append(line);
					}
				} while(line.endsWith("" + env.getMorelinesSymbol()));
			
				String parts[] = sb.toString().split("\\s+");

				if (parts.length == 0)
					continue;
				String commandName = parts[0];
				String arguments = "";
				if (parts.length > 1) {
					arguments = sb.toString().substring(parts[0].length());
				}

				ShellCommand command = env.commands().get(commandName);
				if (command == null) {
					env.writeln("Invalid command name.");
					continue;
				} else {
					status = command.executeCommand(env, arguments);
				}

			} catch (ShellIOException ex) {
				status = ShellStatus.TERMINATE;
			}

			if (status.equals(ShellStatus.TERMINATE))
				break;
		}
	}

}
