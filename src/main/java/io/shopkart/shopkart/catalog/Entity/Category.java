package io.shopkart.shopkart.catalog.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.shopkart.shopkart.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Hierarchical category (Electronics > Phones > Smartphones). Table: categories.
 * Self-referencing: parent_id is NULL for root categories.
 */

@Getter
@Setter
@NoArgsConstructor

@Table(name = "categories")
@Entity
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "slug", nullable = false, unique = true, length = 120)
    private String slug;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Category parent = null;

    @OneToMany(mappedBy = "parent")
//    @OrderBy("sortOrder ASC, name ASC")
    @Setter(lombok.AccessLevel.NONE)
    @JsonManagedReference
    private List<Category> children = new ArrayList<>();

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public void addChild(Category child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(Category child) {
        children.remove(child);
        child.setParent(null);
    }

    public boolean isRoot() {
        return parent == null;
    }

}
