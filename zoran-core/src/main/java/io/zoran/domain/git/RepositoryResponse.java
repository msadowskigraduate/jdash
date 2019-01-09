package io.zoran.domain.git;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 30/12/2018.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryResponse {
    private Long id;
    private String nodeID;
    private String name;
    private String fullName;
    private Boolean welcomePrivate;
    private String htmlURL;
    private String description;
    private Boolean fork;
    private String url;
    private String archiveURL;
    private String assigneesURL;
    private String blobsURL;
    private String branchesURL;
    private String collaboratorsURL;
    private String commentsURL;
    private String commitsURL;
    private String compareURL;
    private String contentsURL;
    private String contributorsURL;
    private String deploymentsURL;
    private String downloadsURL;
    private String eventsURL;
    private String forksURL;
    private String gitCommitsURL;
    private String gitRefsURL;
    private String gitTagsURL;
    private String gitURL;
    private String issueCommentURL;
    private String issueEventsURL;
    private String issuesURL;
    private String keysURL;
    private String labelsURL;
    private String languagesURL;
    private String mergesURL;
    private String milestonesURL;
    private String notificationsURL;
    private String pullsURL;
    private String releasesURL;
    private String sshURL;
    private String stargazersURL;
    private String statusesURL;
    private String subscribersURL;
    private String subscriptionURL;
    private String tagsURL;
    private String teamsURL;
    private String treesURL;
    private String cloneURL;
    private String mirrorURL;
    private String hooksURL;
    private String svnURL;
    private String homepage;
    private Object language;
    private Long forksCount;
    private Long stargazersCount;
    private Long watchersCount;
    private Long size;
    private String defaultBranch;
    private Long openIssuesCount;
    private String[] topics;
    private Boolean hasIssues;
    private Boolean hasProjects;
    private Boolean hasWiki;
    private Boolean hasPages;
    private Boolean hasDownloads;
    private Boolean archived;
    private String pushedAt;
    private String createdAt;
    private String updatedAt;
    private Boolean allowRebaseMerge;
    private Boolean allowSquashMerge;
    private Boolean allowMergeCommit;
    private Long subscribersCount;
    private Long networkCount;
}
