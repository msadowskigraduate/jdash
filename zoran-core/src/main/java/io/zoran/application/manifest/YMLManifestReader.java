package io.zoran.application.manifest;

import io.zoran.core.infrastructure.exception.ManifestReaderException;
import io.zoran.domain.manifest.Manifest;
import io.zoran.domain.manifest.YMLManifest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 22/07/2018.
 */

@Component
public class YMLManifestReader implements ManifestReader {
    @Override
    public boolean canRead(Path path) {
        File f = path.toFile();
        return Files.exists(path) &&
                f.isFile() &&
                (f.getPath().endsWith(".yml") || f.getPath().endsWith(".yaml"));
    }

    @Override
    public Manifest read(Path path) {
        if(canRead(path)) {
            try {
                String yml = StringUtils.join(Files.readAllLines(path), "\n");
                return read(yml);
            } catch (IOException e) {
                throw new ManifestReaderException();
            }
        }
        throw new ManifestReaderException();
    }

    @Override
    public Manifest read(String input) {
        Yaml yaml = new Yaml(new Constructor(YMLManifest.class));
        return yaml.load(input);
    }

    @Override
    public Manifest read(InputStream input) {
        try {
            BufferedInputStream bis = new BufferedInputStream(input);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
            return read(buf.toString("UTF-8"));
        } catch (IOException e) {
            throw new ManifestReaderException();
        }
    }
}
