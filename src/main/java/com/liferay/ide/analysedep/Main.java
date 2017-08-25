package com.liferay.ide.analysedep;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Iterator<File> files =  FileUtils.iterateFiles(new File("/home/andy/work/dev java/projects/liferay-ide/"), new String[]{"MF"}, true);
		
		List<String> totalList = new ArrayList<String>();
		
		while(files.hasNext())
		{
			File file = files.next();
			//System.out.println(file.getAbsolutePath());
			
			List<String> lines = FileUtils.readLines(file,Charset.defaultCharset());
			
			String requireBundle = "Require-Bundle:";
			
			for(String line : lines)
			{
				if(line.contains("bundle-version"))
				{
					if(line.startsWith(requireBundle))
					{
						totalList.add(line.substring(requireBundle.length(), line.length()).trim()+"-"+file.getAbsolutePath());
					}
					else
					{
						totalList.add(line.trim()+"-"+file.getAbsolutePath());
					}
					//System.out.println(line);
				}
			}
		}

		Collections.sort(totalList);

		Set<String> dependencies = new HashSet<String>();
		
		for(String result : totalList)
		{
			if(result.startsWith("com.liferay.ide"))
			{
				System.out.println(result);
			}
			
			
			dependencies.add(result.split(";")[0]);
		}

		List<String> sortedList = new ArrayList<String>(dependencies);
		Collections.sort(sortedList);
		
		for(String dependency : sortedList)
		{
			//System.out.println(dependency);
		}
	}
}
