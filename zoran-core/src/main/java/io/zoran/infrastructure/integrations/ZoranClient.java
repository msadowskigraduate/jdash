package io.zoran.infrastructure.integrations;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 29/07/2018.
 */
@FeignClient("zoran")
interface ZoranClient {
}
