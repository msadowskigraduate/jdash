package io.zoran.application.pipelines.handlers.impl;

import lombok.experimental.UtilityClass;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 03/02/2019.
 */
@UtilityClass
final class HandlerParamConst {
    public static final String DEFAULT_COMMIT_MESSAGE =
            "This commit contains code generated from templates using Zoran.io! <3";

    public static final String ENV_VAR = "ENV_VAR";
    public static final String GIT_COMMAND = "GIT_COMMAND";
    public static final String GIT_PATH = "GIT_PATH";
    public static final String GIT_URL = "GIT_URL";
    public static final String CLONED_LOCAL_PATH = "CLONED_LOCAL_PATH";
    public static final String LOCAL_ITEMS_MOVED = "LOCAL_ITEMS_MOVED";
}
