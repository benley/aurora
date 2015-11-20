/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.aurora.common.net.pool;

import com.google.common.collect.ImmutableSet;

import org.apache.aurora.common.base.Command;

/**
 * A host set that can be monitored for changes.
 *
 * @param <T> The type that is used to identify members of the host set.
 */
public interface DynamicHostSet<T> {

  /**
   * Registers a monitor to receive change notices for this server set as long as this jvm process
   * is alive.  Blocks until the initial server set can be gathered and delivered to the monitor.
   * The monitor will be notified if the membership set or parameters of existing members have
   * changed.
   *
   * @param monitor the server set monitor to call back when the host set changes
   * @return A command which, when executed, will stop monitoring the host set.
   * @throws MonitorException if there is a problem monitoring the host set
   */
  Command watch(final HostChangeMonitor<T> monitor) throws MonitorException;

  /**
   * An interface to an object that is interested in receiving notification whenever the host set
   * changes.
   */
  interface HostChangeMonitor<T> {

    /**
     * Called when either the available set of services changes (when a service dies or a new
     * instance comes on-line) or when an existing service advertises a status or health change.
     *
     * @param hostSet the current set of available ServiceInstances
     */
    void onChange(ImmutableSet<T> hostSet);
  }

  class MonitorException extends Exception {
    public MonitorException(String msg) {
      super(msg);
    }

    public MonitorException(String msg, Throwable cause) {
      super(msg, cause);
    }
  }
}
