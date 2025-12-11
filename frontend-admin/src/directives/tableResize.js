/**
 * 表格列宽调整指令
 * 使用方法：在 el-table 上添加 v-table-resize
 */
export default {
  mounted(el) {
    if (!el) return;
    
    const header = el.querySelector('.el-table__header-wrapper thead');
    if (!header) return;

    const ths = header.querySelectorAll('th');
    let startX = 0;
    let startWidth = 0;
    let currentTh = null;
    let isResizing = false;

    // 为每个 th 添加调整手柄
    ths.forEach((th, index) => {
      // 跳过选择列和操作列（固定列）
      if (th.querySelector('.el-checkbox') || th.classList.contains('el-table__fixed-right-patch') || th.classList.contains('is-fixed-right')) {
        return;
      }

      // 检查是否已经有调整手柄
      if (th.querySelector('.resize-handle')) {
        return;
      }

      const resizeHandle = document.createElement('div');
      resizeHandle.className = 'resize-handle';
      resizeHandle.style.cssText = `
        position: absolute;
        right: 0;
        top: 0;
        bottom: 0;
        width: 5px;
        cursor: col-resize;
        z-index: 10;
        background: transparent;
      `;
      
      th.style.position = 'relative';
      th.appendChild(resizeHandle);

      // 鼠标按下事件
      resizeHandle.addEventListener('mousedown', (e) => {
        e.preventDefault();
        e.stopPropagation();
        isResizing = true;
        startX = e.clientX;
        startWidth = th.offsetWidth;
        currentTh = th;
        document.body.style.cursor = 'col-resize';
        document.body.style.userSelect = 'none';
      });
    });

    // 鼠标移动事件
    document.addEventListener('mousemove', (e) => {
      if (!isResizing || !currentTh) return;
      
      const diff = e.clientX - startX;
      const newWidth = Math.max(50, startWidth + diff); // 最小宽度50px
      currentTh.style.width = newWidth + 'px';
      currentTh.style.minWidth = newWidth + 'px';
      
      // 更新表格列宽
      const colIndex = Array.from(currentTh.parentNode.children).indexOf(currentTh);
      const bodyTable = el.querySelector('.el-table__body-wrapper table');
      const fixedRightTable = el.querySelector('.el-table__fixed-right table');
      
      // 更新主体表格
      if (bodyTable) {
        const tds = bodyTable.querySelectorAll(`tbody tr td:nth-child(${colIndex + 1})`);
        tds.forEach(td => {
          td.style.width = newWidth + 'px';
          td.style.minWidth = newWidth + 'px';
        });
      }
      
      // 更新固定列表格（如果有）
      if (fixedRightTable && colIndex < fixedRightTable.querySelectorAll('thead th').length) {
        const fixedThs = fixedRightTable.querySelectorAll(`thead th:nth-child(${colIndex + 1})`);
        const fixedTds = fixedRightTable.querySelectorAll(`tbody tr td:nth-child(${colIndex + 1})`);
        fixedThs.forEach(th => {
          th.style.width = newWidth + 'px';
          th.style.minWidth = newWidth + 'px';
        });
        fixedTds.forEach(td => {
          td.style.width = newWidth + 'px';
          td.style.minWidth = newWidth + 'px';
        });
      }
    });

    // 鼠标释放事件
    document.addEventListener('mouseup', () => {
      if (isResizing) {
        isResizing = false;
        currentTh = null;
        document.body.style.cursor = '';
        document.body.style.userSelect = '';
      }
    });
  }
};

