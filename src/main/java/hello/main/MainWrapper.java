package hello.main;

import com.google.googlejavaformat.java.Main;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWrapper {

	private static String TAG = MainWrapper.class.getSimpleName();

	public static void main(String[] args) {
		new MainWrapper().run(args);
	}

	private void run(String[] args) {

		ExecutorService executorService = Executors.newSingleThreadExecutor();

		WrapperOptions parameters = processArgs(args);
		File rootDir = new File(parameters.dir());
		walk(executorService, rootDir);

		executorService.shutdown();
	}

	private void walk(ExecutorService executorService, File dir) {
		File[] files = dir.listFiles();
		LinkedList<String> newArgs = new LinkedList<>();

		for (File f : files) {
			if (f.isDirectory()) {
				walk(executorService, f);
			} else {
				String name = f.getName();
				int dot = name.lastIndexOf('.');
				String extension = (dot == -1) ? "" : name.substring(dot + 1);
				if (extension.equalsIgnoreCase("java")) {
					String absPath = f.getAbsolutePath();
					newArgs.add(absPath);
				}
			}
		}

		if (newArgs.size() > 0) {
			newArgs.push("-aosp"); // Use AOSP style(4-space indentation).
			newArgs.push("-replace"); // Send formatted output back to files, not stdout.

			Thread t = new Thread(new Main(newArgs.toArray(new String[0])));
			executorService.submit(t);
		}
	}

	private WrapperOptions processArgs(String[] args) {
		WrapperOptions parameters = WrapperOptionsParser.parse(Arrays.asList(args));
		return parameters;
	}
}
