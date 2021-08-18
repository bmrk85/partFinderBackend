package hu.bmrk.geposszerakobackend.engine.arukereso;

import hu.bmrk.geposszerakobackend.model.dto.ProductInfoResponseDTO;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

@Component
public class PriceAndShopFinderModule {

    public ProductInfoResponseDTO findPriceAndShop(String URL) throws IOException {

        HtmlCleaner cleaner = new HtmlCleaner();

        TagNode node = cleaner.clean(new URL(URL));
        TagNode[] itemPriceNodes = node.getElementsByAttValue("itemprop", "price", true, true);


        Optional<Integer> lowestPrice = Arrays.stream(itemPriceNodes).map(tn -> Integer.valueOf(tn.getAttributeByName("content")))
                .min(Integer::compareTo);
        if(lowestPrice.isPresent()) {

            TagNode[] lowestPriceNode = node.getElementsByAttValue("content", lowestPrice.get().toString(), true, true);
            TagNode parentOfLowestPriceNode = Arrays.stream(lowestPriceNode).map(tn -> tn.getParent().getParent().getParent()).findAny().get(); //todo: do smth here
            String shopName;

            Optional<TagNode> shopLogoNode = Arrays.stream(parentOfLowestPriceNode.getElementsHavingAttribute("alt", true)).findFirst();
            if (shopLogoNode.isPresent()) {
                shopName = this.createShopName(shopLogoNode.get().getAttributeByName("alt").split(" "));
            } else {
                TagNode shopNode = Arrays.stream(parentOfLowestPriceNode.getElementsByAttValue("class", "logo-host", true, true)).findFirst().get();
                ContentNode shopNameNode = (ContentNode) shopNode.getAllChildren().get(0);
                shopName = shopNameNode.getContent();
            }

            return ProductInfoResponseDTO.builder()
                    .cheapestPrice(lowestPrice.get())
                    .cheapestShop(shopName)
                    .build();
        }else{
            return ProductInfoResponseDTO.builder().cheapestPrice(0).cheapestShop("Out of stock everywhere").build();
        }
    }

    private String createShopName(String[] arrName){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<arrName.length-1;i++){
            sb.append(arrName[i]);
        }
        return sb.toString();
    }

}
