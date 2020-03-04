/*
 * Copyright Nathaniel Fanning (c) 2020. All rights reserved.
 */

import java.io.File;

/**
 * @author Nathaniel Fanning
 * @version 0.1
 * @since 0.1
 */

public final class CONSTANTS {
    public static final String ApplicationSupportName = "CobbAnalyzerData";
    public static final String userPath = System.getProperty("user.home");
    public static final String datalogPath = userPath + File.separator + "Library/Application Support/" + ApplicationSupportName + File.separator;
    public static final String ApplicationSupportPath = userPath + File.separator + "Library/Application Support/" + ApplicationSupportName;
}
