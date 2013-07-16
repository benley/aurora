package com.twitter.aurora.scheduler.thrift;

import java.util.Set;

import com.google.inject.Inject;

import com.twitter.aurora.scheduler.thrift.auth.CapabilityValidator.Capability;
import com.twitter.aurora.scheduler.thrift.auth.Requires;
import com.twitter.mesos.gen.CommitRecoveryResponse;
import com.twitter.mesos.gen.CreateJobResponse;
import com.twitter.mesos.gen.DeleteRecoveryTasksResponse;
import com.twitter.mesos.gen.DrainHostsResponse;
import com.twitter.mesos.gen.EndMaintenanceResponse;
import com.twitter.mesos.gen.FinishUpdateResponse;
import com.twitter.mesos.gen.ForceTaskStateResponse;
import com.twitter.mesos.gen.GetJobUpdatesResponse;
import com.twitter.mesos.gen.GetJobsResponse;
import com.twitter.mesos.gen.GetQuotaResponse;
import com.twitter.mesos.gen.Hosts;
import com.twitter.mesos.gen.JobConfiguration;
import com.twitter.mesos.gen.JobKey;
import com.twitter.mesos.gen.KillResponse;
import com.twitter.mesos.gen.ListBackupsResponse;
import com.twitter.mesos.gen.MaintenanceStatusResponse;
import com.twitter.mesos.gen.MesosAdmin;
import com.twitter.mesos.gen.PerformBackupResponse;
import com.twitter.mesos.gen.PopulateJobResponse;
import com.twitter.mesos.gen.QueryRecoveryResponse;
import com.twitter.mesos.gen.Quota;
import com.twitter.mesos.gen.RestartShardsResponse;
import com.twitter.mesos.gen.RewriteConfigsRequest;
import com.twitter.mesos.gen.RewriteConfigsResponse;
import com.twitter.mesos.gen.RollbackShardsResponse;
import com.twitter.mesos.gen.ScheduleStatus;
import com.twitter.mesos.gen.ScheduleStatusResponse;
import com.twitter.mesos.gen.SessionKey;
import com.twitter.mesos.gen.SetQuotaResponse;
import com.twitter.mesos.gen.SnapshotResponse;
import com.twitter.mesos.gen.StageRecoveryResponse;
import com.twitter.mesos.gen.StartCronResponse;
import com.twitter.mesos.gen.StartMaintenanceResponse;
import com.twitter.mesos.gen.StartUpdateResponse;
import com.twitter.mesos.gen.TaskQuery;
import com.twitter.mesos.gen.UnloadRecoveryResponse;
import com.twitter.mesos.gen.UpdateResult;
import com.twitter.mesos.gen.UpdateShardsResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Aurora scheduler thrift implementation. Performs routing between Thrift calls and
 * controllers. For now this is a thin shim to avoid huge refactors for simple Thrift API
 * changes and input validation is performed by the controllers.
 */
public class SchedulerThriftRouter implements MesosAdmin.Iface {

  private SchedulerController schedulerController;

  @Inject
  SchedulerThriftRouter(SchedulerController schedulerController) {
    this.schedulerController = checkNotNull(schedulerController);
  }

  @Override
  public CreateJobResponse createJob(JobConfiguration job, SessionKey session) {
    return schedulerController.createJob(job, session);
  }

  @Override
  public PopulateJobResponse populateJobConfig(JobConfiguration description) {
    return schedulerController.populateJobConfig(description);
  }

  @Override
  public StartCronResponse startCronJob(JobKey jobKey, SessionKey session) {
    return schedulerController.startCronJob(jobKey, session);
  }

  @Override
  public ScheduleStatusResponse getTasksStatus(TaskQuery query) {
    return schedulerController.getTasksStatus(query);
  }

  @Override
  public GetJobsResponse getJobs(String ownerRole) {
    return schedulerController.getJobs(ownerRole);
  }

  @Override
  public KillResponse killTasks(final TaskQuery query, SessionKey session) {
    return schedulerController.killTasks(query, session);
  }

  @Override
  public StartUpdateResponse startUpdate(JobConfiguration job, SessionKey session) {
    return schedulerController.startUpdate(job, session);
  }

  @Override
  public UpdateShardsResponse updateShards(
      JobKey jobKey,
      Set<Integer> shards,
      String updateToken,
      SessionKey session) {

    return schedulerController.updateShards(jobKey, shards, updateToken, session);
  }

  @Override
  public RollbackShardsResponse rollbackShards(
      JobKey jobKey,
      Set<Integer> shards,
      String updateToken,
      SessionKey session) {

    return schedulerController.rollbackShards(jobKey, shards, updateToken, session);
  }

  @Override
  public FinishUpdateResponse finishUpdate(
      JobKey jobKey,
      UpdateResult updateResult,
      String updateToken,
      SessionKey session) {

    return schedulerController.finishUpdate(jobKey, updateResult, updateToken, session);
  }

  @Override
  public RestartShardsResponse restartShards(
      JobKey jobKey,
      Set<Integer> shardIds,
      SessionKey session) {

    return schedulerController.restartShards(jobKey, shardIds, session);
  }

  @Override
  public GetQuotaResponse getQuota(String ownerRole) {
    return schedulerController.getQuota(ownerRole);
  }

  @Override
  public StartMaintenanceResponse startMaintenance(Hosts hosts, SessionKey session) {
    return schedulerController.startMaintenance(hosts, session);
  }

  @Override
  public DrainHostsResponse drainHosts(Hosts hosts, SessionKey session) {
    return schedulerController.drainHosts(hosts, session);
  }

  @Override
  public MaintenanceStatusResponse maintenanceStatus(Hosts hosts, SessionKey session) {
    return schedulerController.maintenanceStatus(hosts, session);
  }

  @Override
  public EndMaintenanceResponse endMaintenance(Hosts hosts, SessionKey session) {
    return schedulerController.endMaintenance(hosts, session);
  }

  @Requires(whitelist = Capability.PROVISIONER)
  @Override
  public SetQuotaResponse setQuota(String ownerRole, Quota quota, SessionKey session) {
    return schedulerController.setQuota(ownerRole, quota, session);
  }

  @Override
  public ForceTaskStateResponse forceTaskState(
      String taskId,
      ScheduleStatus status,
      SessionKey session) {

    return schedulerController.forceTaskState(taskId, status, session);
  }

  @Override
  public PerformBackupResponse performBackup(SessionKey session) {
    return schedulerController.performBackup(session);
  }

  @Override
  public ListBackupsResponse listBackups(SessionKey session) {
    return schedulerController.listBackups(session);
  }

  @Override
  public StageRecoveryResponse stageRecovery(String backupId, SessionKey session) {
    return schedulerController.stageRecovery(backupId, session);
  }

  @Override
  public QueryRecoveryResponse queryRecovery(TaskQuery query, SessionKey session) {
    return schedulerController.queryRecovery(query, session);
  }

  @Override
  public DeleteRecoveryTasksResponse deleteRecoveryTasks(TaskQuery query, SessionKey session) {
    return schedulerController.deleteRecoveryTasks(query, session);
  }

  @Override
  public CommitRecoveryResponse commitRecovery(SessionKey session) {
    return schedulerController.commitRecovery(session);
  }

  @Override
  public UnloadRecoveryResponse unloadRecovery(SessionKey session) {
    return schedulerController.unloadRecovery(session);
  }

  @Override
  public GetJobUpdatesResponse getJobUpdates(SessionKey session) {
    return schedulerController.getJobUpdates(session);
  }

  @Override
  public SnapshotResponse snapshot(SessionKey session) {
    return schedulerController.snapshot(session);
  }

  @Override
  public RewriteConfigsResponse rewriteConfigs(RewriteConfigsRequest request, SessionKey session) {
    return schedulerController.rewriteConfigs(request, session);
  }
}