import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CrudApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrudApiApplication.class, args);
    }
}

@RestController
@RequestMapping("/items")
class ItemController {

    private final Map<String, Item> items = new HashMap<>();

    @GetMapping
    public Collection<Item> getItems() {
        return items.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable String id) {
        Item item = items.get(id);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item newItem) {
        if (items.containsKey(newItem.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        items.put(newItem.getId(), newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Item updatedItem) {
        if (!items.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedItem.setId(id); // Ensure ID consistency
        items.put(id, updatedItem);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        if (!items.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        items.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

class Item {
    private String id;
    private String name;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
