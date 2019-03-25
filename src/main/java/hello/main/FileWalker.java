package hello.main;

import hello.processor.IProcessor;
import java.io.File;

public class FileWalker {

	private static final String TAG = FileWalker.class.getSimpleName();

	public void walk() throws Exception {

		String rootDir = new File(".").getAbsolutePath();
		File inputDir = new File(rootDir + "/input");
		File[] list = inputDir.listFiles();

		IProcessor p =
				new IProcessor() {

					@Override
					public void doIt(File file) throws Exception {}

					@Override
					public void writeFile(File file) throws Exception {}
				};

		if (list != null) {
			for (File f : list) {
				if (f.isDirectory()) {

				} else {
					String name = f.getName();
					int dot = name.lastIndexOf('.');
					String extension = (dot == -1) ? "" : name.substring(dot + 1);

					p.doIt(f);
					p.writeFile(f);
				}
			}
		}
	}
}
