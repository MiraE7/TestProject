document.addEventListener("DOMContentLoaded", function() {
    const itemPriceInput = document.getElementById("item-price");
    const itemSizeInput = document.getElementById("item-size");
    const minusButton = document.querySelector(".minus-btn");
    const plusButton = document.querySelector(".plus-btn");
    const totalPriceInput = document.getElementById("total-price");
  

  
    
    // Update total price when size changes
    itemSizeInput.addEventListener("change", function() {
      updateTotalPrice();
    });
  
    // Decrease size when minus button is clicked
    minusButton.addEventListener("click", function() {
      if (itemSizeInput.value > 0) {
        itemSizeInput.value--;
        updateTotalPrice();
      }
    });
  
    // Increase size when plus button is clicked
    plusButton.addEventListener("click", function() {
        itemSizeInput.value++;
      updateTotalPrice();
    });
  
    // Update total price based on item price and 
    function updateTotalPrice() {
      const itemPrice = parseFloat(itemPriceInput.value);
      const size= parseInt(itemSizeInput.value);
      const totalPrice = itemPrice * size;
      totalPriceInput.value = totalPrice.toFixed(2);
    }
  });

  function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
  }
  
  function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
  }