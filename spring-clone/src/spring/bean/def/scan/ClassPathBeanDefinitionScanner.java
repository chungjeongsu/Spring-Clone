package spring.bean.def.scan;

import spring.bean.def.BeanDefinition;
import spring.bean.def.GenericBeanDefinitionRegistry;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassPathBeanDefinitionScanner {
    private final GenericBeanDefinitionRegistry genericBeanDefinitionRegistry;
    private final BeanDefinitionParser beanDefinitionParser;
    private final ClassLoader classLoader;

    private BeanNameGenerator beanNameGenerator;

    public ClassPathBeanDefinitionScanner(GenericBeanDefinitionRegistry genericBeanDefinitionRegistry, BeanDefinitionParser beanDefinitionParser) {
        this.genericBeanDefinitionRegistry = genericBeanDefinitionRegistry;
        this.beanDefinitionParser = beanDefinitionParser;
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public void scan(String... basePackages) {
        Map<String, BeanDefinition> beanDefinitions = new LinkedHashMap<>();

        for(String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);

        }
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) throws URISyntaxException, IOException {
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();

        String basePackagePath = basePackage.replace('.', '/');

        Enumeration<URL> resources = classLoader.getResources(basePackagePath);

        while(resources.hasMoreElements()) {
            URL url = resources.nextElement();
            doScan(url, basePackage, beanDefinitions);
        }

        return beanDefinitions;
    }

    private void doScan(URL url, String basePackage, Set<BeanDefinition> beanDefinitions) throws URISyntaxException {
        if (url.getProtocol().equals("file")) {  //파일 시스템 파일 일 시
            doScanFile(url.toURI(), basePackage, beanDefinitions);
            return;
        }
        if(url.getProtocol().equals("jar")) {   //배포 jar일 시
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            doScanJar(jarURLConnection, beanDefinitions);
        }
    }

    private void doScanFile(URI uri, String currPackage, Set<BeanDefinition> beanDefinitions) {
        File file = new File(uri);
        File[] files = file.listFiles();

        if(files == null) throw new RuntimeException();

        if(files.length >= 1) {
            for(File child : files) {
                if(child.isDirectory()) {
                    String subPackage = currPackage + "." + child.getName();
                    doScanFile(child.toURI(), subPackage, beanDefinitions);
                    continue;
                }

                if(!child.getName().endsWith(".class") || child.getName().contains("$")) continue;

                String childSimpleName = child.getName().substring(0, child.getName().length() - 6);
                String className = currPackage + "." + childSimpleName;

                try{
                    Class<?> clazz = classLoader.loadClass(className);

                    BeanDefinition parsedBeanDefinition = beanDefinitionParser.parse(clazz);
                    if(parsedBeanDefinition != null) beanDefinitions.add(parsedBeanDefinition);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void doScanJar(JarURLConnection jarURLConnection, Set<BeanDefinition> beanDefinitions) {
        JarFile jarFile = jarURLConnection.getJarFile();

        Enumeration<JarEntry> entries = jarFile.entries();
        while(entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();

            if(entry.isDirectory()) continue;
            if(!name.endsWith(".class") || name.contains("$")) continue;

            String className = name.substring(0, name.length() - 6).replace('/','.');

            Class<?> clazz = classLoader.loadClass(className);
            BeanDefinition parsedBeanDefinition = beanDefinitionParser.parse(clazz);
            if(parsedBeanDefinition != null) beanDefinitions.add(parsedBeanDefinition);
        }
    }
}
