using System;
using System.Collections.Generic;

namespace Web.Entities
{
    public class File : IElement
    {
        public int Id { get; set; } = 0;
        public string Name { get; set; } = String.Empty;
        public string Owner { get; set; } = String.Empty;
        public  virtual ICollection<Content> ContentsHistory { get; set; } 
        public Accessibility Accessibility { get; set; } = Accessibility.Private;
    }
}