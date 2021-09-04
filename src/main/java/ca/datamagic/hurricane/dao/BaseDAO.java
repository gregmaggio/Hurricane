/**
 * 
 */
package ca.datamagic.hurricane.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import com.google.common.collect.Lists;

/**
 * @author Greg
 *
 */
public abstract class BaseDAO {
	private static String appKey = null;
	private static GoogleCredentials credentials = null;
	
	public static synchronized String getAppKey() {
        return appKey;
    }

    public static synchronized void setAppKey(String newVal) {
        appKey = newVal;
        credentials = null;
    }
    
    public static synchronized GoogleCredentials getCredentials() throws IOException {
    	if (credentials == null) {
    		if ((appKey != null) && (appKey.length() > 0)) {
	    		credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(appKey.getBytes()))
	                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
    		}
    	}
    	return credentials;
    }
    
    public static TableResult runQuery(String query) throws IOException, InterruptedException {
        BigQuery bigQuery = BigQueryOptions.newBuilder().setCredentials(getCredentials()).build().getService();
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(query)
                        // Use standard SQL syntax for queries.
                        // See: https://cloud.google.com/bigquery/sql-reference/
                        .setUseLegacySql(false)
                        .build();
        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString().toUpperCase());
        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
        queryJob = queryJob.waitFor();

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        // Get the results
        return queryJob.getQueryResults();
    }
}
