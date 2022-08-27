package org.bassani.examplebff.mock;

import org.bassani.examplemodellib.domain.request.ProductSupplierRequest;
import org.bassani.examplemodellib.domain.response.ProductSupplierResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.util.List;

public class ProductSupplierMock implements TemplateLoader {

		public static final ProductSupplierRequest PRODUCT_SUPPLIER_REQUEST_COMPLETE = new ProductSupplierRequest(List.of(6L), List.of(331L), List.of(555L));
		public static final ProductSupplierRequest PRODUCT_SUPPLIER_REQUEST_NULL = new ProductSupplierRequest(null, null, null);
		public static final ProductSupplierRequest PRODUCT_SUPPLIER_REQUEST_SUPPLIER_ID_NULL = new ProductSupplierRequest(null, List.of(331L), List.of(555L));
		
		@Override
		public void load() {
			buildProductSupplierResponseComplete();
		}

		public static List<ProductSupplierResponse> mockedResponse() {

	        List<ProductSupplierResponse> response = Fixture.from(ProductSupplierResponse.class).gimme(5, "ProductSupplierResponseComplete");

			return response;
		}

		private void buildProductSupplierResponseComplete() {
		
		Fixture.of(ProductSupplierResponse.class).addTemplate("ProductSupplierResponseComplete", new Rule() {
			{
				add("productId", random(Long.class, range(200L, 205L)));
				add("productDescription", name());
			}
		});

	}
}
