/**
 * Copyright (c) 2023, WSO2 LLC. (https://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.oauth.par.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.par.dao.ParMgtDAO;
import org.wso2.carbon.identity.oauth.par.dao.ParMgtDAOImpl;
import org.wso2.carbon.identity.oauth.par.exceptions.ParCoreException;
import org.wso2.carbon.identity.oauth.par.model.ParRequestCacheEntry;
import org.wso2.carbon.identity.oauth.par.model.ParRequestDO;

import java.util.Map;

/**
 * Caching layer for PAR Requests.
 */
public class CacheBackedParDAO implements ParMgtDAO {

    private static final Log log = LogFactory.getLog(CacheBackedParDAO.class);
    private final ParCache parCache = ParCache.getInstance();
    private final ParMgtDAOImpl parMgtDAO = new ParMgtDAOImpl();

    @Override
    public void persistRequestData(String reqUriRef, String clientId, long expiresIn,
                                   Map<String, String> parameters) throws ParCoreException {

        ParRequestCacheEntry parRequestCacheEntry = new ParRequestCacheEntry(reqUriRef, parameters,
                expiresIn, clientId);
        parMgtDAO.persistRequestData(reqUriRef, clientId, expiresIn, parameters);
        parCache.addToCache(reqUriRef, parRequestCacheEntry);
    }

    @Override
    public ParRequestDO getRequestData(String reqUriRef) throws ParCoreException {

        ParRequestCacheEntry parCacheRequest = parCache.getValueFromCache(reqUriRef);
        ParRequestDO parRequestDO;
        if (parCacheRequest != null) {
            if (log.isDebugEnabled()) {
                log.debug("Cache miss for expiry time of local uuid: %s for tenant:%s " + reqUriRef);
            }
            parRequestDO = new ParRequestDO(parCacheRequest);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Cache hit for expiry time of uuid:%s for tenant:%s " + reqUriRef);
            }
            parRequestDO = parMgtDAO.getRequestData(reqUriRef);
        }
        return parRequestDO;
    }

    @Override
    public void removeRequestData(String reqUriRef) throws ParCoreException {

        parCache.clearCacheEntry(reqUriRef);
        parMgtDAO.removeRequestData(reqUriRef);
    }
}
