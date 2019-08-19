package io.zoran.api.domain;

import io.zoran.domain.manifest.Context;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 05.03.2019
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateMetadata {
   private String templateSlug;
   private List<Context> contexts;
}
