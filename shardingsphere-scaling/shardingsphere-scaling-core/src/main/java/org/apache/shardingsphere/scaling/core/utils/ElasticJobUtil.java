/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.scaling.core.utils;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.governance.repository.api.config.GovernanceConfiguration;
import org.apache.shardingsphere.scaling.core.config.ScalingContext;
import org.apache.shardingsphere.scaling.core.constant.ScalingConstant;

import java.util.Properties;

/**
 * Elastic job util.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ElasticJobUtil {
    
    /**
     * Create registry center.
     *
     * @return coordinator registry center
     */
    public static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter result = new ZookeeperRegistryCenter(getZookeeperConfig());
        result.init();
        return result;
    }
    
    private static ZookeeperConfiguration getZookeeperConfig() {
        GovernanceConfiguration governanceConfig = ScalingContext.getInstance().getServerConfig().getGovernanceConfig();
        ZookeeperConfiguration result = new ZookeeperConfiguration(governanceConfig.getRegistryCenterConfiguration().getServerLists(),
                governanceConfig.getName() + ScalingConstant.SCALING_ELASTIC_JOB_PATH);
        Properties props = governanceConfig.getRegistryCenterConfiguration().getProps();
        result.setMaxSleepTimeMilliseconds(getProperty(props, "max.sleep.time.milliseconds", result.getMaxSleepTimeMilliseconds()));
        result.setBaseSleepTimeMilliseconds(getProperty(props, "base.sleep.time.milliseconds", result.getBaseSleepTimeMilliseconds()));
        result.setConnectionTimeoutMilliseconds(getProperty(props, "connection.timeout.milliseconds", result.getConnectionTimeoutMilliseconds()));
        result.setSessionTimeoutMilliseconds(getProperty(props, "session.timeout.milliseconds", result.getSessionTimeoutMilliseconds()));
        return result;
    }
    
    private static int getProperty(final Properties props, final String key, final int defaultValue) {
        return Strings.isNullOrEmpty(props.getProperty(key)) ? defaultValue : Integer.parseInt(props.getProperty(key));
    }
}
