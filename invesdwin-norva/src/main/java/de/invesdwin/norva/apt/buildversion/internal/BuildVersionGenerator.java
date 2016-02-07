package de.invesdwin.norva.apt.buildversion.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import de.invesdwin.norva.apt.buildversion.BuildVersionDefinition;

@NotThreadSafe
public class BuildVersionGenerator implements Runnable {

    private final ProcessingEnvironment env;
    private final BuildVersionDefinition def;

    public BuildVersionGenerator(final ProcessingEnvironment env, final BuildVersionDefinition def) {
        this.env = env;
        this.def = def;
    }

    @Override
    public void run() {
        // create facade class file
        final String staticFacadeClassStr = def.name();
        final String buildVersionPackage = staticFacadeClassStr.substring(0, staticFacadeClassStr.lastIndexOf("."));
        final String buildVersionClassName = staticFacadeClassStr.substring(staticFacadeClassStr.lastIndexOf(".") + 1);
        // write file content
        final String content = generateContent(buildVersionPackage, buildVersionClassName);

        final FileObject fileObject;
        try {
            fileObject = env.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, buildVersionPackage,
                    buildVersionClassName + ".java");

            final Writer gen = fileObject.openWriter();
            try {
                gen.write(content);
            } finally {
                gen.close();
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateContent(final String staticFacadePackage, final String buildVersionClassName) {
        final StringBuilder sb = new StringBuilder();
        sb.append("package " + staticFacadePackage + ";");
        sb.append("\n");
        sb.append("\npublic abstract class " + buildVersionClassName + " {");
        sb.append("\n");
        sb.append("\n\t/**");
        sb.append("\n\t * ");
        //CHECKSTYLE:OFF
        sb.append(new Date());
        //CHECKSTYLE:ON
        sb.append("\n\t */");
        sb.append("\n\tpublic static final String STATIC_VERSION = \"");
        final long utcTime = System.currentTimeMillis();
        sb.append(utcTime);
        sb.append("\";");
        sb.append("\n");
        sb.append("\n\tpublic static String getDynamicVersion() {");
        sb.append("\n\t\treturn STATIC_VERSION;");
        sb.append("\n\t}");
        sb.append("\n");
        sb.append("\n}");
        sb.append("\n");
        return sb.toString();
    }

}
