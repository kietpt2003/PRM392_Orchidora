// TransactionHistory.java
package com.example.prm391_orchidora.Models.TransactionHistory;

import java.util.List;

public class TransactionHistory {
    private String id;
    private String accountName;
    private String phoneNumber;
    private String address;
    private String status;
    private long createdAt;
    private List<Item> items;
    private OrderPayment orderPayment;

    // Constructor, getter và setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OrderPayment getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(OrderPayment orderPayment) {
        this.orderPayment = orderPayment;
    }

    public class Item {
        private Orchid orchid;
        private String name;
        private int price;
        private int quantity;

        // Constructor, getter và setter

        public Orchid getOrchid() {
            return orchid;
        }

        public void setOrchid(Orchid orchid) {
            this.orchid = orchid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public class Orchid {
            private String id;
            private int price;
            private Category category;
            private String color;
            private String description;
            private String img;
            private int quantity;
            private String status;
            private String name;

            // Constructor, getter và setter

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public Category getCategory() {
                return category;
            }

            public void setCategory(Category category) {
                this.category = category;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public class Category {
                private String id;
                private String name;

                // Constructor, getter và setter

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
            }
        }
    }

    public class OrderPayment {
        private int amount;
        private String paymentMethod;
        private Object paidOn;
        private int orderCode;

        // Constructor, getter và setter

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public Object getPaidOn() {
            return paidOn;
        }

        public void setPaidOn(Object paidOn) {
            this.paidOn = paidOn;
        }

        public int getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(int orderCode) {
            this.orderCode = orderCode;
        }
    }
}


