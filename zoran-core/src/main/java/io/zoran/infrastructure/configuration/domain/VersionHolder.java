package io.zoran.infrastructure.configuration.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 30/11/2018.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class VersionHolder {
    private final String tags;                    // =${git.tags} // comma separated tag names
    private final String branch;                  // =${git.branch}
    private final String dirty;                   // =${git.dirty}
    private final String remoteOriginUrl;         // =${git.remote.origin.url}
    private final String commitId;                // =${git.commit.id.full} OR ${git.commit.id}
    private final String commitIdAbbrev;          // =${git.commit.id.abbrev}
    private final String describe;                // =${git.commit.id.describe}
    private final String describeShort;           // =${git.commit.id.describe-short}
    private final String commitUserName;          // =${git.commit.user.name}
    private final String commitUserEmail;         // =${git.commit.user.email}
    private final String commitMessageFull;       // =${git.commit.message.full}
    private final String commitMessageShort;      // =${git.commit.message.short}
    private final String commitTime;              // =${git.commit.time}
    private final String closestTagName;          // =${git.closest.tag.name}
    private final String closestTagCommitCount;   // =${git.closest.tag.commit.count}
    private final String buildUserName;           // =${git.build.user.name}
    private final String buildUserEmail;          // =${git.build.user.email}
    private final String buildTime;               // =${git.build.time}
    private final String buildHost;               // =${git.build.host}
    private final String buildVersion;            // =${git.build.version}
    private final String buildNumber;             // =${git.build.number}
    private final String buildNumberUnique;

    public VersionHolder() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("git.properties"));

        this.tags = String.valueOf(properties.get("git.tags"));
        this.branch = String.valueOf(properties.get("git.branch"));
        this.dirty = String.valueOf(properties.get("git.dirty"));
        this.remoteOriginUrl = String.valueOf(properties.get("git.remote.origin.url"));
        this.commitId = String.valueOf(properties.get("git.commit.id.full")); // OR properties.get("git.commit.id") depending on your configuration
        this.commitIdAbbrev = String.valueOf(properties.get("git.commit.id.abbrev"));
        this.describe = String.valueOf(properties.get("git.commit.id.describe"));
        this.describeShort = String.valueOf(properties.get("git.commit.id.describe-short"));
        this.commitUserName = String.valueOf(properties.get("git.commit.user.name"));
        this.commitUserEmail = String.valueOf(properties.get("git.commit.user.email"));
        this.commitMessageFull = String.valueOf(properties.get("git.commit.message.full"));
        this.commitMessageShort = String.valueOf(properties.get("git.commit.message.short"));
        this.commitTime = String.valueOf(properties.get("git.commit.time"));
        this.closestTagName = String.valueOf(properties.get("git.closest.tag.name"));
        this.closestTagCommitCount = String.valueOf(properties.get("git.closest.tag.commit.count"));
        this.buildUserName = String.valueOf(properties.get("git.build.user.name"));
        this.buildUserEmail = String.valueOf(properties.get("git.build.user.email"));
        this.buildTime = String.valueOf(properties.get("git.build.time"));
        this.buildHost = String.valueOf(properties.get("git.build.host"));
        this.buildVersion = String.valueOf(properties.get("git.build.version"));
        this.buildNumber = String.valueOf(properties.get("git.build.number"));
        this.buildNumberUnique = String.valueOf(properties.get("git.build.number.unique"));
    }
}
